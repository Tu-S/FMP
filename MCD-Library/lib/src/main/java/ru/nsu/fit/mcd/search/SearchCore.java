package ru.nsu.fit.mcd.search;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ru.nsu.fit.mcd.search.report.FieldReport;
import ru.nsu.fit.mcd.utils.Pair;

public class SearchCore {

  private static Set<Type> getGenericTypeArgumentsFromType(Type checkedType) {
    if (checkedType instanceof ParameterizedType) {
      return Arrays.stream(((ParameterizedType) checkedType).getActualTypeArguments())
          .collect(Collectors.toSet());
    }
    return Set.of();
  }

  private static Set<Class<?>> getClassesFromGenericType(Type type) {
    Deque<Type> typesToProcess = new LinkedList<>(List.of(type));
    Set<Class<?>> classes = new HashSet<>();

    while (!typesToProcess.isEmpty()) {
      Type currentType = typesToProcess.pop();
      if (currentType instanceof Class<?>) {
        classes.add((Class<?>) currentType);
      }
      typesToProcess.addAll(getGenericTypeArgumentsFromType(currentType));
    }

    return classes;
  }

  private static Pair<FieldReport, Set<Class<?>>> getClassesAndReportFromField(Field field) {
    Type fieldType = field.getGenericType();

    return new Pair<>(
        new FieldReport(field.getName(), fieldType.getTypeName()),
        getClassesFromGenericType(fieldType)
    );
  }

  private static Set<Class<?>> getClassesFromParent(Class<?> targetClass) {
    return getClassesFromGenericType(targetClass.getGenericSuperclass());
  }


}
