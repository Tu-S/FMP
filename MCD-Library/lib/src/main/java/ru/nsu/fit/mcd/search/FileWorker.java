package ru.nsu.fit.mcd.search;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWorker {

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

  public void writeToFile(String fileName, String content) throws IOException {
    try (FileWriter writer = new FileWriter(fileName, false)) {
      writer.write(content);
      writer.flush();
    }
  }
}
