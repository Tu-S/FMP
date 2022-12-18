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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.nsu.fit.mcd.search.report.AggregatedClassReport;
import ru.nsu.fit.mcd.search.report.ClassReport;
import ru.nsu.fit.mcd.search.report.FieldReport;
import ru.nsu.fit.mcd.utils.Pair;

class ClassSearchCore {

  private static final Set<String> CLASS_PACKAGE_BLACKLIST = Set.of(
      "java."
  );

  private static Set<Type> getGenericTypeArgumentsFromType(Type targetType) {
    if (targetType instanceof ParameterizedType) {
      return Arrays.stream(((ParameterizedType) targetType).getActualTypeArguments())
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
      } else if (currentType instanceof ParameterizedType) {
        classes.add((Class<?>) ((ParameterizedType) currentType).getRawType());
      }
      typesToProcess.addAll(getGenericTypeArgumentsFromType(currentType));
    }

    return classes;
  }

  private static Pair<FieldReport, Set<Class<?>>> getClassesAndReportFromField(Field field) {
    Type fieldType = field.getGenericType();

    return Pair.of(
        new FieldReport(field.getName(), fieldType.getTypeName()),
        getClassesFromGenericType(fieldType)
    );
  }

  private static Set<Class<?>> getClassesFromParent(Class<?> targetClass) {
    if (targetClass.isPrimitive() || targetClass.isArray()) {
      return Set.of();
    }
    return getClassesFromGenericType(targetClass.getGenericSuperclass());
  }

  private static Pair<ClassReport, Set<Class<?>>> processClass(Class<?> targetClass) {
    var fieldsReport = Arrays.stream(targetClass.getDeclaredFields())
        .map(ClassSearchCore::getClassesAndReportFromField).collect(Collectors.toList());

    var parentClasses = getClassesFromParent(targetClass);
    var finalClassSet = Stream.concat(
        fieldsReport.stream()
            .map(Pair::getValue)
            .flatMap(Collection::stream),
        parentClasses.stream()
    ).collect(Collectors.toSet());
    var parentClassName = "";
    if (!targetClass.isPrimitive() && !targetClass.isArray()) {
      parentClassName =
          targetClass.getGenericSuperclass() != null ? targetClass.getGenericSuperclass()
              .getTypeName() : "";
    }

    return Pair.of(
        new ClassReport(
            targetClass.getName(),
            fieldsReport.stream().map(Pair::getKey).sorted().collect(Collectors.toList()),
            parentClassName
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
              .filter(Predicate.not(scannedClasses::containsKey))
              .filter(Predicate.not(Class::isArray))
              .filter(Predicate.not(Class::isPrimitive))
              .filter(Predicate.not(Class::isAnonymousClass))
              .filter(c -> CLASS_PACKAGE_BLACKLIST.stream()
                  .noneMatch(s -> c.getName().startsWith(s)))
              .collect(Collectors.toSet())
      );
    }

    return scannedClasses.values().stream().sorted().collect(Collectors.toList());
  }

  public static AggregatedClassReport getAggregatedClassReport(Class<?> targetClass) {
    var reports = getClassReport(targetClass);
    ClassReport target = reports.stream().filter(x -> x.getClassName() == targetClass.getName())
        .findFirst().get();
    reports.remove(target);
    return new AggregatedClassReport(target.getClassName(), target.getFields(),
        target.getParentClassName(), reports);
  }
}
