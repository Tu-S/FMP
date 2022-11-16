package ru.nsu.fit.mcd.search.report;

import java.util.Objects;

public class ArgumentReport implements Comparable<ArgumentReport>{

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

  @Override
  public int compareTo(ArgumentReport o) {
    return type.compareTo(o.type);
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
    ArgumentReport other = (ArgumentReport) obj;
    return Objects.equals(type, other.type);
  }
}
