package ru.nsu.fit.mcd.search;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import ru.nsu.fit.mcd.search.report.ClassMethodsReport;
import ru.nsu.fit.mcd.search.report.ClassReport;
import ru.nsu.fit.mcd.search.report.MethodReport;

public class FileWorker {

  public boolean writeReportHashToFile(String fileName, ClassReport report) throws IOException {
    boolean success = false;
    try (FileWriter writer = new FileWriter(fileName, false)) {
      writer.write(report.toString().hashCode());
      writer.flush();
      success = true;
    }

    return success;
  }

  public boolean writeReportHashToFile(String fileName, ClassMethodsReport report) throws IOException {
    boolean success = false;
    try (FileWriter writer = new FileWriter(fileName, false)) {
      int hash = report.toString().hashCode();
      writer.write(String.valueOf(hash));
      writer.flush();
      success = true;
    }

    return success;
  }

  public int readReportHashCode(String fileName) throws IOException {

    int hash = -1;
    try (FileReader reader = new FileReader(fileName)) {
      Scanner scan = new Scanner(reader);
      while (scan.hasNextInt()) {
        hash = scan.nextInt();
      }
    }

    return hash;
  }
}
