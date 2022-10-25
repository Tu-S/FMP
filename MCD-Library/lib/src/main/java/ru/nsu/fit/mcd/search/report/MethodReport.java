package ru.nsu.fit.mcd.search.report;

import java.util.List;

public class MethodReport implements Comparable<MethodReport> {

  private String methodName;
  private List<ArgumentReport> arguments;
  private ArgumentReport returnType;
  private List<ClassReport> classReports;

  public MethodReport(String methodName, List<ArgumentReport> arguments, ArgumentReport returnType,
      List<ClassReport> classReports) {
    this.methodName = methodName;
    this.arguments = arguments;
    this.returnType = returnType;
    this.classReports = classReports;
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
    return "MethodReport{" +
        "methodName='" + methodName + '\'' +
        ", returnType='" + returnType + '\'' +
        ", arguments=" + arguments + '\'' +
        ", classes=" + classReports + '}';
  }

  @Override
  public int compareTo(MethodReport o) {
    return methodName.compareTo(o.methodName);
  }
}
