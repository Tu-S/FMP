package ru.nsu.fit.mcd.search;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.mcd.search.report.FieldReport;
import ru.nsu.fit.mcd.search.report.MethodReport;

public class MethodSearchCore {

  public static List<MethodReport> getMethodsReport(Class<?> targetClass) {
    Map<Class<?>, FieldReport> scannedClasses = new HashMap<>();
    Deque<Class<?>> classesToScan = new LinkedList<>(List.of(targetClass));

    var methodsReports = Arrays.stream(targetClass.getDeclaredMethods())
        .map(MethodSearchCore::processMethod);

    return methodsReports.collect(Collectors.toList());
  }

  private static MethodReport processMethod(Method targetMethod) {
    String methodName = targetMethod.getName();

    var returnedClass = targetMethod.getReturnType();
    var parametersClassesReports = Arrays.stream(targetMethod.getParameterTypes())
        .map(SearchCore::getClassReport).flatMap(
            Collection::stream).collect(Collectors.toSet());
    var returnedClassReport = SearchCore.getClassReport(returnedClass);

    return new MethodReport(
        methodName,
        parametersClassesReports.stream().toList(),
        returnedClassReport);
  }
}
