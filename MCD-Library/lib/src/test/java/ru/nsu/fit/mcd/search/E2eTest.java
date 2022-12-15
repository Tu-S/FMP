package ru.nsu.fit.mcd.search;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.mcd.TestClass1;
import ru.nsu.fit.mcd.TestClass2;
import ru.nsu.fit.mcd.utils.DiffUtils;
import ru.nsu.fit.mcd.utils.ReportSaver;

public class E2eTest {

  @Test
  public void ClassReportEqualTest() throws IOException {
    var classReportsReportClass1 = ClassSearchCore.getAggregatedClassReport(TestClass1.class);
    ReportSaver saver = new ReportSaver();
    FileWorker fileWorker = new FileWorker();
    assertDoesNotThrow(() -> saver.saveClassReport(classReportsReportClass1));
    var savedHash = fileWorker.readReportHashCode(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.hash.txt");
    var hash = classReportsReportClass1.hashCode();
    assertEquals(hash, savedHash);
    var savedString = fileWorker.readFromFile(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.txt");
    var stringRep = classReportsReportClass1.toString();
    var diffs = DiffUtils.getDiffs(savedString, stringRep);
    assertEquals(1, diffs.size());
    assertEquals(diffs.get(0).operation.toString(), "EQUAL");
    var html = DiffUtils.GetHtml(diffs);
    fileWorker.writeToFile("src/test/ClassReportEqualTest.html", html);
  }

  @Test
  public void ClassReportNotEqualTest() throws IOException {
    var classReportsReportClass1 = ClassSearchCore.getAggregatedClassReport(TestClass1.class);
    var classReportsReportClass2 = ClassSearchCore.getAggregatedClassReport(TestClass2.class);
    ReportSaver saver = new ReportSaver();
    FileWorker fileWorker = new FileWorker();
    assertDoesNotThrow(() -> saver.saveClassReport(classReportsReportClass1));
    var savedHash = fileWorker.readReportHashCode(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.hash.txt");
    var hash = classReportsReportClass2.hashCode();
    assertNotEquals(hash, savedHash);
    var savedString = fileWorker.readFromFile(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.txt");
    var stringRep = classReportsReportClass2.toString();
    var diffs = DiffUtils.getDiffs(savedString, stringRep);
    assertNotEquals(1, diffs.size());
    var html = DiffUtils.GetHtml(diffs);
    fileWorker.writeToFile("src/test/ClassReportNotEqualTest.html", html);
  }

  @Test
  public void ClassReportChangedTest() throws IOException {
    var classReportsReportClass2 = ClassSearchCore.getAggregatedClassReport(TestClass2.class);
    FileWorker fileWorker = new FileWorker();
    var hash = classReportsReportClass2.hashCode();
    var savedHash = fileWorker.readReportHashCode(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.hash.txt");
    assertNotEquals(hash, savedHash);
    var savedString = fileWorker.readFromFile(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.txt");
    var stringRep = classReportsReportClass2.toString();
    var diffs = DiffUtils.getDiffs(savedString, stringRep);
    assertNotEquals(1, diffs.size());
    var html = DiffUtils.GetHtml(diffs);
    fileWorker.writeToFile("src/test/ClassReportChangedTest.html", html);
  }

  @Test
  public void ClassMethodsReportEqualTest() throws IOException {
    var classReportsReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    ReportSaver saver = new ReportSaver();
    FileWorker fileWorker = new FileWorker();
    assertDoesNotThrow(() -> saver.saveClassMethodsReport(classReportsReportClass1));
    var savedHash = fileWorker.readReportHashCode(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.methods.hash.txt");
    var hash = classReportsReportClass1.hashCode();
    assertEquals(hash, savedHash);
    var savedString = fileWorker.readFromFile(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.methods.txt");
    var stringRep = classReportsReportClass1.toString();
    var diffs = DiffUtils.getDiffs(savedString, stringRep);
    assertEquals(1, diffs.size());
    assertEquals(diffs.get(0).operation.toString(), "EQUAL");
    var html = DiffUtils.GetHtml(diffs);
    fileWorker.writeToFile("src/test/ClassMethodsReportEqualTest.html", html);
  }

  @Test
  public void ClassMethodsReportNotEqualTest() throws IOException {
    var classReportsReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    var classReportsReportClass2 = MethodSearchCore.getMethodsReport(TestClass2.class);
    ReportSaver saver = new ReportSaver();
    FileWorker fileWorker = new FileWorker();
    assertDoesNotThrow(() -> saver.saveClassMethodsReport(classReportsReportClass1));
    var savedHash = fileWorker.readReportHashCode(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.methods.hash.txt");
    var hash = classReportsReportClass2.hashCode();
    assertNotEquals(hash, savedHash);
    var savedString = fileWorker.readFromFile(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.methods.txt");
    var stringRep = classReportsReportClass2.toString();
    var diffs = DiffUtils.getDiffs(savedString, stringRep);
    assertNotEquals(1, diffs.size());
    var html = DiffUtils.GetHtml(diffs);
    fileWorker.writeToFile("src/test/ClassMethodsReportNotEqualTest.html", html);
  }

  @Test
  public void ClassMethodsReportChangedTest() throws IOException {
    var classReportsReportClass2 = MethodSearchCore.getMethodsReport(TestClass2.class);
    FileWorker fileWorker = new FileWorker();
    var hash = classReportsReportClass2.hashCode();
    var savedHash = fileWorker.readReportHashCode(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.methods.hash.txt");
    assertNotEquals(hash, savedHash);
    var savedString = fileWorker.readFromFile(
        "reports/ru.nsu.fit.mcd.TestClass1/ru.nsu.fit.mcd.TestClass1.methods.txt");
    var stringRep = classReportsReportClass2.toString();
    var diffs = DiffUtils.getDiffs(savedString, stringRep);
    assertNotEquals(1, diffs.size());
    var html = DiffUtils.GetHtml(diffs);
    fileWorker.writeToFile("src/test/ClassMethodsReportChangedTest.html", html);
  }
}
