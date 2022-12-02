package ru.nsu.fit.mcd.search.report;

import java.util.List;
import java.util.Objects;

public class ClassReport implements Comparable<ClassReport> {

  private String className;
  private List<FieldReport> fields;
  private String parentClass;

  public ClassReport(String className, List<FieldReport> fields, String parentClass) {
    this.className = className;
    this.fields = fields;
    this.parentClass = parentClass;
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

  public String getParentClassName() {
    return this.parentClass;
  }

  @Override
  public String toString() {

    var builder = new StringBuilder();
    builder.append("[Class name: ").append(className).append("]\n").append("Fields:\n");
    builder.append("Parent: ").append(parentClass).append("\n");
    for (var field : fields) {
      builder.append(field.toString()).append("\n");
    }
    return builder.toString();
  }

  @Override
  public int compareTo(ClassReport o) {
    return className.compareTo(o.className);
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
    ClassReport other = (ClassReport) obj;
    return Objects.equals(className, other.className);
  }
}
