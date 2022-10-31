package ru.nsu.fit.mcd.search;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.nsu.fit.mcd.search.report.ArgumentReport;
import ru.nsu.fit.mcd.search.report.MethodReport;

public class MethodSearchCore {

  public static List<MethodReport> getMethodsReport(Class<?> targetClass) {
    var methodsReports = Arrays.stream(targetClass.getDeclaredMethods())
        .map(MethodSearchCore::processMethod);

    return methodsReports.collect(Collectors.toList());
  }

  private static MethodReport processMethod(Method targetMethod) {
    String methodName = targetMethod.getName();

    var parameterTypes = targetMethod.getParameterTypes();
    var genericParameterTypes = targetMethod.getGenericParameterTypes();
    var genericReturnType = targetMethod.getGenericReturnType();
    var argsReport = Arrays.stream(genericParameterTypes)
        .map(p -> new ArgumentReport("arg", p.getTypeName())).collect(Collectors.toList());
    var returnedClass = targetMethod.getReturnType();
    var returnedTypeReport = new ArgumentReport("returned", genericReturnType.getTypeName());

    var returnedClassReport = SearchCore.getClassReport(returnedClass);
    var classes = Stream.concat(Arrays.stream(parameterTypes)
            .map(SearchCore::getClassReport).flatMap(
                Collection::stream),
        returnedClassReport.stream()).collect(Collectors.toSet()).stream().toList();

    return new MethodReport(
        methodName,
        argsReport,
        returnedTypeReport,
        classes
    );
  }
}
