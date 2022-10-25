package ru.nsu.fit.mcd.search.report;

import java.util.List;

public class MethodReport implements Comparable<MethodReport> {

  private String methodName;
  private List<ClassReport> arguments;
  private List<ClassReport> returnType;

  public MethodReport(String methodName, List<ClassReport> arguments, List<ClassReport> returnType) {
    this.methodName = methodName;
    this.arguments = arguments;
    this.returnType = returnType;
  }

  @Override
  public String toString() {
    return "MethodReport{" +
        "methodName='" + methodName + '\'' +
        ", returnType='" + returnType + '\'' +
        ", arguments=" + arguments + '}';
  }

  @Override
  public int compareTo(MethodReport o) {
    return methodName.compareTo(o.methodName);
  }
}
