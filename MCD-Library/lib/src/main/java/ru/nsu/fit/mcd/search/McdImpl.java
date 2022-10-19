package ru.nsu.fit.mcd.search;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * MCD implementation.
 */
public class McdImpl {

  public String getMethodStructureReport(Class<?> target, String methodName) {
    return null;
  }

  public String getObjectStructureReport(Class<?> targetClass) throws IOException {
    var classesToProcess = new LinkedList<Class<?>>();
    classesToProcess.add(targetClass);

    Map<String, List<SimpleEntry<String, String>>> map = new HashMap<>();

    while (!classesToProcess.isEmpty()) {
      var target = classesToProcess.pop();

      if (
          target == null
              || target.isPrimitive()
              || Objects.equals(target.getModule().getName(), "java.base")
      ) {
        continue;
      }

      var fieldsList = new ArrayList<SimpleEntry<String, String>>();

      Arrays.stream(target.getDeclaredFields()).forEach(field -> {
        Class<?> fieldType = field.getType();
        var componentType = fieldType.getComponentType();
        if (componentType != null) {
          classesToProcess.add(componentType);
        }
        Type fieldGenericType = field.getGenericType();
        if (fieldGenericType instanceof ParameterizedType parameterizedFieldType) {
          Arrays.stream(parameterizedFieldType.getActualTypeArguments())
              .forEach(actualArgumentType -> {
                if (actualArgumentType instanceof Class<?>) {
                  classesToProcess.add((Class<?>) actualArgumentType);
                } else {
                  if (actualArgumentType instanceof ParameterizedType) {
                    classesToProcess.add(
                        (Class) ((ParameterizedType) actualArgumentType).getRawType());
                  }
                }
              });
          fieldsList.add(
              new SimpleEntry<>(field.getName(), parameterizedFieldType.getTypeName()));
          classesToProcess.add((Class) parameterizedFieldType.getRawType());
        } else {
          classesToProcess.add(fieldType);
          fieldsList.add(new SimpleEntry<>(field.getName(), field.getType().getName()));
        }
      });

      var parentClass = target.getSuperclass();

      if (parentClass != null) {
        classesToProcess.add(parentClass);
      }
      fieldsList.sort(Comparator.comparing(AbstractMap.SimpleEntry<String, String>::getValue));
      map.put(target.getName(), fieldsList);
    }

    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
  }
}
