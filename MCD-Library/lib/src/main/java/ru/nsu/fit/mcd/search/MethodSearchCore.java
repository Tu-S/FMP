package ru.nsu.fit.mcd.search;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.nsu.fit.mcd.search.report.ArgumentReport;
import ru.nsu.fit.mcd.search.report.ClassMethodsReport;
import ru.nsu.fit.mcd.search.report.MethodReport;

public class MethodSearchCore {

  public static ClassMethodsReport getMethodsReport(Class<?> targetClass) {
    var methodsReports = Arrays.stream(targetClass.getDeclaredMethods())
        .map(MethodSearchCore::processMethod);

    return new ClassMethodsReport(targetClass.getName(), methodsReports.sorted().collect(Collectors.toList()));
  }

  private static MethodReport processMethod(Method targetMethod) {
    String methodName = targetMethod.getName();

    var parameterTypes = targetMethod.getParameterTypes();
    var genericParameterTypes = targetMethod.getGenericParameterTypes();
    var genericReturnType = targetMethod.getGenericReturnType();
    targetMethod.getReturnType();
    var returnTypeName = targetMethod.getReturnType().getTypeName();
    var argsReport = Arrays.stream(genericParameterTypes)
        .map(p -> new ArgumentReport("arg", p.getTypeName())).sorted().collect(Collectors.toList());
    var returnedClass = targetMethod.getReturnType();
    var returnedTypeReport = new ArgumentReport("returned", genericReturnType.getTypeName());

    var returnedClassReport = ClassSearchCore.getClassReport(returnedClass);
    var classes = Stream.concat(Arrays.stream(parameterTypes)
            .map(ClassSearchCore::getClassReport).flatMap(
                Collection::stream),
        returnedClassReport.stream()).collect(Collectors.toSet()).stream().sorted().toList();

    return new MethodReport(
        methodName,
        argsReport,
        returnedTypeReport,
        classes
    );
  }
}
