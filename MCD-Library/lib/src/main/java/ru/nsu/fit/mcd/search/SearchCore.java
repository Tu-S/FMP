package ru.nsu.fit.mcd.search;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.nsu.fit.mcd.search.report.ClassReport;
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
    if (type == null) {
      return Set.of();
    }

    Deque<Type> typesToProcess = new LinkedList<>(List.of(type));
    Set<Class<?>> classes = new HashSet<>();

    while (!typesToProcess.isEmpty()) {
      Type currentType = typesToProcess.pop();
      if (currentType instanceof Class<?>) {
        classes.add((Class<?>) currentType);
      }
      if (currentType instanceof ParameterizedType parameterizedType) {
        classes.add((Class) parameterizedType.getRawType());
      }
      typesToProcess.addAll(getGenericTypeArgumentsFromType(currentType));
    }

    return classes;
  }

  private static Pair<FieldReport, Set<Class<?>>> getClassesAndReportFromField(Field field) {
    Type fieldGenericType = field.getGenericType();

    return Pair.of(
        new FieldReport(field.getName(), fieldGenericType.getTypeName()),
        getClassesFromGenericType(fieldGenericType)
    );
  }

  private static Set<Class<?>> getClassesFromParent(Class<?> targetClass) {
    return getClassesFromGenericType(targetClass.getGenericSuperclass());
  }

  private static Pair<ClassReport, Set<Class<?>>> processClass(Class<?> targetClass) {
    Stream<Pair<FieldReport, Set<Class<?>>>> fieldsReport = Arrays.stream(
            targetClass.getDeclaredFields())
        .map(SearchCore::getClassesAndReportFromField);

    var fieldReportList = fieldsReport.toList();

    var fieldsReportPairs = fieldReportList.stream().map(Pair::getValue);

    var parentClasses = getClassesFromParent(targetClass);
    var finalClassSet = Stream.concat(
        fieldsReportPairs
            .flatMap(Collection::stream),
        parentClasses.stream()
    ).collect(Collectors.toSet());
    var sortedFieldsReport = fieldReportList.stream().map(Pair::getKey).sorted();
    return Pair.of(
        new ClassReport(
            targetClass.getName(),
            sortedFieldsReport.collect(Collectors.toList())
        ),
        finalClassSet
    );
  }

  public static List<ClassReport> getClassReport(Class<?> targetClass) {
    Map<Class<?>, ClassReport> scannedClasses = new HashMap<>();
    Deque<Class<?>> classesToScan = new LinkedList<>(List.of(targetClass));

    while (!classesToScan.isEmpty()) {
      var currentClass = classesToScan.pop();
      var report = processClass(currentClass);

      scannedClasses.put(currentClass, report.getKey());
      classesToScan.addAll(
          report.getValue().stream()
              .filter(c -> !scannedClasses.containsKey(c))
              .collect(Collectors.toSet())
      );
    }

    return scannedClasses.values().stream()
        .filter(cl -> cl.getClassName().startsWith("ru.nsu.fit.mcd")).sorted()
        .collect(Collectors.toList());
  }
}
