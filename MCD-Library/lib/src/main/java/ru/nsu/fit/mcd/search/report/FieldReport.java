package ru.nsu.fit.mcd.search.report;

public class FieldReport {

  private String fieldName;
  private String fieldTypeName;

  public FieldReport(String fieldName, String fieldTypeName) {
    this.fieldName = fieldName;
    this.fieldTypeName = fieldTypeName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldTypeName() {
    return fieldTypeName;
  }

  public void setFieldTypeName(String fieldTypeName) {
    this.fieldTypeName = fieldTypeName;
  }

  @Override
  public String toString() {
    return "FieldReport{" +
        "fieldName='" + fieldName + '\'' +
        ", fieldTypeName='" + fieldTypeName + '\'' +
        '}';
  }
}
