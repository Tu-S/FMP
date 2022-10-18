package ru.nsu.fit.mcd.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class McdImpl implements Mcd {

    @Override
    public String getMethodHash(Class target, String methodName) {
        return null;
    }

    @Override
    public String getObjectHash(Class initialClass) throws JsonProcessingException {
        var classesToProcess = new LinkedList<Class>();
        var processedClasses = new LinkedList<Class>();
        classesToProcess.add(initialClass);

        while(!classesToProcess.isEmpty())
        {
            var target = classesToProcess.pop();

            if(target == null || target.isPrimitive())
                continue;

            processedClasses.add(target);

            for (var field: target.getDeclaredFields()) {
                Class<?> fieldType = field.getType();

                var componentType = fieldType.getComponentType();

                if(componentType != null)
                    classesToProcess.add(componentType);

                Type fieldGenericType = field.getGenericType();

                if(fieldGenericType instanceof ParameterizedType)
                {
                    ParameterizedType aType = (ParameterizedType) fieldGenericType;
                    for(Type t : aType.getActualTypeArguments())
                    {
                        classesToProcess.add((Class<?>)t);
                    }

                    classesToProcess.add((Class) aType.getRawType());
                }
                else
                {
                    classesToProcess.add(field.getType());
                }
            }

            var parentClass = target.getSuperclass();

            if(parentClass != null)
                classesToProcess.add(parentClass);
        }

        Map<String, List<AbstractMap.SimpleEntry<String, String>>> map = new HashMap<String, List<AbstractMap.SimpleEntry<String, String>>>();
        for(Class cl : processedClasses)
        {
            var fields = Arrays.stream(
                    cl.getDeclaredFields()).map(
                            field -> {
                                String fieldTypeName = null;
                                Type fieldGenericType = field.getGenericType();
                                if(fieldGenericType instanceof ParameterizedType)
                                    fieldTypeName = fieldGenericType.getTypeName();
                                else
                                    fieldTypeName = field.getType().getName();
                                return new AbstractMap.SimpleEntry<String, String>(
                                        field.getName(),
                                        fieldTypeName);

                            })

                    .toList();
            map.put(cl.getName(), fields);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String resultJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        return  resultJsonStr;
    }
}
