package ru.nsu.fit.mcd.search.report;

import java.util.List;

public class ClassReport implements Comparable<ClassReport> {

  private String className;
  private List<FieldReport> fields;

  public ClassReport(String className, List<FieldReport> fields) {
    this.className = className;
    this.fields = fields;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public List<FieldReport> getFields() {
    return fields;
  }

  public void setFields(List<FieldReport> fields) {
    this.fields = fields;
  }

  @Override
  public String toString() {
    return "\nClassReport{\n" +
        "  className='" + className + "',\n" +
        "  fields=" + fields + "'\n" +
        '}';
  }

  @Override
  public int compareTo(ClassReport o) {
    return className.compareTo(o.className);
  }
}
