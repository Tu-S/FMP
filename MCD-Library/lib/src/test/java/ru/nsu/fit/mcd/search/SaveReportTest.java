package ru.nsu.fit.mcd.search;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.mcd.TestClass1;
import ru.nsu.fit.mcd.TestClass2;
import ru.nsu.fit.mcd.utils.DiffUtils;
import ru.nsu.fit.mcd.utils.ReportSaver;

public class SaveReportTest {
  @Test
  public void SaveClassMethodReport()
  {
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);

    ReportSaver saver = new ReportSaver();
    assertDoesNotThrow(
        () -> saver.saveClassMethodsReport(methodReportClass1));
  }

  @Test
  public void SaveClass1Report()
  {
    var classReportsReportClass1 = ClassSearchCore.getAggregatedClassReport(TestClass1.class);
    ReportSaver saver = new ReportSaver();
    assertDoesNotThrow(
        () -> saver.saveClassReport(classReportsReportClass1));
  }

  @Test
  public void SaveClass1WithDiff() throws IOException {
    var classReportsReportClass1 = ClassSearchCore.getAggregatedClassReport(TestClass1.class);
    var classReportsReportClass2 = ClassSearchCore.getAggregatedClassReport(TestClass2.class);
    var html = DiffUtils.getClassReportsDiffsHtml(classReportsReportClass1, classReportsReportClass2);
    ReportSaver saver = new ReportSaver();
    assertDoesNotThrow(() -> saver.saveDiffsHtml(classReportsReportClass1.getClassName(),html));
  }

  @Test
  public void SaveClass1WithoutDiff() throws IOException {
    var classReportsReportClass1 = ClassSearchCore.getAggregatedClassReport(TestClass1.class);
    var html = DiffUtils.getClassReportsDiffsHtml(classReportsReportClass1, classReportsReportClass1);
    ReportSaver saver = new ReportSaver();
    assertDoesNotThrow(() -> saver.saveDiffsHtml(classReportsReportClass1.getClassName(),html));
  }
}
