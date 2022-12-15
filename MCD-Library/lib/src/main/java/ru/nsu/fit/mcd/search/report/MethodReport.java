package ru.nsu.fit.mcd.search.report;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MethodReport implements Comparable<MethodReport> {

  private String methodName;
  private List<ArgumentReport> arguments;
  private ArgumentReport returnType;
  private List<ClassReport> classReports;
  private Annotation[] annotations;

  public MethodReport(
      String methodName,
      List<ArgumentReport> arguments,
      ArgumentReport returnType,
      List<ClassReport> classReports,
      Annotation[] annotations) {
    this.methodName = methodName;
    this.arguments = arguments;
    this.returnType = returnType;
    this.classReports = classReports;
    this.annotations = annotations;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public List<ArgumentReport> getArguments() {
    return arguments;
  }

  public void setArguments(List<ArgumentReport> arguments) {
    this.arguments = arguments;
  }

  public ArgumentReport getReturnType() {
    return returnType;
  }

  public void setReturnType(ArgumentReport returnType) {
    this.returnType = returnType;
  }

  public List<ClassReport> getClassReports() {
    return classReports;
  }

  public void setClassReports(List<ClassReport> classReports) {
    this.classReports = classReports;
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append("[Method " + methodName + "]\n");
    for (var a : this.annotations) {
      builder.append("Annotation: " + a.toString() + "\n");
    }
    for (var arg : arguments) {
      builder.append(arg.toString() + "\n");
    }
    builder.append("Returned: " + returnType.toString());
    builder.append("\n");
    builder.append("Involved classes: {\n");
    for (var clazz : classReports.stream().distinct().collect(Collectors.toList())) {
      builder.append(clazz.toString() + "\n");
    }
    builder.append("}");
    return builder.toString();
  }

  @Override
  public int compareTo(MethodReport o) {
    return methodName.compareTo(o.methodName);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MethodReport other = (MethodReport) obj;
    return Objects.equals(methodName, other.methodName);
  }
}
