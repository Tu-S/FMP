package ru.nsu.fit.mcd.search.report;

import java.util.List;

public class AggregatedClassReport extends ClassReport {

  private List<ClassReport> usedClasses;

  public AggregatedClassReport(
      String className,
      List<FieldReport> fields,
      String parentClass,
      List<ClassReport> usedClasses) {
    super(className, fields, parentClass);
    this.usedClasses = usedClasses;
  }

  @Override
  public String toString() {
    var superString = super.toString();
    var builder = new StringBuilder(superString);
    builder.append("\nInvolved classes: {\n");
    for (var clazz : this.usedClasses) {
      builder.append(clazz.toString()).append("\n");
    }
    builder.append("}\n");
    return builder.toString();
  }
}
