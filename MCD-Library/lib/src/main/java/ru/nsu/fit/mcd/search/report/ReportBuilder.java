package ru.nsu.fit.mcd.search.report;

import java.util.List;

public class ReportBuilder {

  public static String BuildClassReport(List<ClassReport> classes) {
    var builder = new StringBuilder();
    for (var clazz: classes){
      builder.append("{" + clazz.toString()+"\n}"+"\n\n");
    }

    return builder.toString();
  }

  public static String BuildMethodsReport(List<MethodReport> methodReports) {
    var builder = new StringBuilder();
    for (var methodReport: methodReports){
      builder.append(methodReport.toString()+"\n\n");
    }

    return builder.toString();
  }

}
