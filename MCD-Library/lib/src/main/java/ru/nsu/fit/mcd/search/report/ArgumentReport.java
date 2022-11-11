package ru.nsu.fit.mcd.search.report;

public class ArgumentReport {

  private String name;
  private String type;

  public ArgumentReport(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Argument type: " + type;
  }
}
