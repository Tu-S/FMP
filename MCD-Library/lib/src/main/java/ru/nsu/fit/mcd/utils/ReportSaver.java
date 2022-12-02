package ru.nsu.fit.mcd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import ru.nsu.fit.mcd.search.FileWorker;
import ru.nsu.fit.mcd.search.report.AggregatedClassReport;
import ru.nsu.fit.mcd.search.report.ClassMethodsReport;
import ru.nsu.fit.mcd.search.report.ClassReport;
import ru.nsu.fit.mcd.search.report.MethodReport;

public class ReportSaver {

  private FileWorker writer;
  private static final String PATH = System.getProperty("user.dir") + "/reports";

  public ReportSaver() {
    this.writer = new FileWorker();
  }

  public void saveClassReport(AggregatedClassReport report) throws IOException {
    String stringRepresentation = report.toString();
    ObjectMapper objectMapper = new ObjectMapper();
    String clazzName = report.getClassName();
    String json = objectMapper.writeValueAsString(report);
    int hash = report.hashCode();
    String path = PATH + "/" + clazzName;
    File theDir = new File(path);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    writer.writeToFile(path + "/" + clazzName + ".txt", stringRepresentation);
    writer.writeToFile(path + "/" + clazzName + ".json", json);
    writer.writeToFile(path + "/" + clazzName + ".hash.txt", String.valueOf(hash));
  }

  public void saveClassMethodsReport(ClassMethodsReport report) throws IOException {
    String stringRepresentation = report.toString();
    ObjectMapper objectMapper = new ObjectMapper();
    String clazzName = report.getClazzName();
    String json = objectMapper.writeValueAsString(report);
    int hash = report.hashCode();
    String path =  PATH + "/" + clazzName;
    File theDir = new File(path);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    writer.writeToFile(path + "/" + clazzName + ".methods.txt", stringRepresentation);
    writer.writeToFile(path + "/" + clazzName + ".methods.json", json);
    writer.writeToFile(path + "/" + clazzName + ".methods.hash.txt", String.valueOf(hash));
  }

  public void saveDiffsHtml(String clazzName, String html) throws IOException {
    String path =  PATH + "/" + clazzName;
    File theDir = new File(path);
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    writer.writeToFile(path + "/" + clazzName + ".diff.html", html);
  }
}
