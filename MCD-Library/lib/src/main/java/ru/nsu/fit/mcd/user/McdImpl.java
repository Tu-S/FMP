package ru.nsu.fit.mcd.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.GenericArrayType;
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
        classesToProcess.add(initialClass);
        var types = new LinkedList<Type>();

        Map<String, List<AbstractMap.SimpleEntry<String, String>>> map = new HashMap<String, List<AbstractMap.SimpleEntry<String, String>>>();

        while (!classesToProcess.isEmpty()) {
            var target = classesToProcess.pop();

            if (target == null || target.isPrimitive() || Objects.equals(target.getModule().getName(), "java.base"))
                continue;

            var fieldsList = new ArrayList<AbstractMap.SimpleEntry<String, String>>();

            for (var field : target.getDeclaredFields()) {
                Class<?> fieldType = field.getType();

                var componentType = fieldType.getComponentType();

                if (componentType != null)
                    classesToProcess.add(componentType);

                Type fieldGenericType = field.getGenericType();

                if (fieldGenericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedFieldType = (ParameterizedType) fieldGenericType;
                    for (Type actualArgumentType : parameterizedFieldType.getActualTypeArguments()) {
                        if (actualArgumentType instanceof Class<?>)
                            classesToProcess.add((Class<?>) actualArgumentType);
                        else {
                            var tClass = actualArgumentType.getClass();
                            var tTypeName = actualArgumentType.getTypeName();
                            if (actualArgumentType instanceof ParameterizedType)
                                classesToProcess.add((Class) ((ParameterizedType) actualArgumentType).getRawType());
                        }
                    }
                    fieldsList.add(new AbstractMap.SimpleEntry<>(field.getName(), parameterizedFieldType.getTypeName()));
                    classesToProcess.add((Class) parameterizedFieldType.getRawType());
                } else {
                    classesToProcess.add(fieldType);
                    fieldsList.add(new AbstractMap.SimpleEntry<>(field.getName(), field.getType().getName()));
                }
            }

            var parentClass = target.getSuperclass();

            if (parentClass != null)
                classesToProcess.add(parentClass);
            fieldsList.sort(Comparator.comparing(AbstractMap.SimpleEntry<String, String>::getValue));
            map.put(target.getName(), fieldsList);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String resultJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        return resultJsonStr;
    }
}
