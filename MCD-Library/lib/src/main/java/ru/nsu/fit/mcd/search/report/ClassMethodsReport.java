package ru.nsu.fit.mcd.search.report;

import java.util.List;

public class ClassMethodsReport {

  private List<MethodReport> methodReportList;

  private String clazzName;

  public ClassMethodsReport(String name, List<MethodReport> methodReports) {
    this.clazzName = name;
    this.methodReportList = methodReports;
  }

  public List<MethodReport> getMethodReportList() {
    return methodReportList;
  }

  public String getClazzName() {
    return clazzName;
  }

  @Override
  public String toString() {

    var builder = new StringBuilder();
    builder.append("[Class name: ").append(clazzName).append("]\n");

    for (var method : methodReportList) {
      builder.append(method.toString()).append("\n");
    }
    return builder.toString();
  }
}
