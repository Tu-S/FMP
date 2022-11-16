package ru.nsu.fit.mcd.search;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.mcd.TestClass1;

public class WriterTest {

  @Test
  void write() throws IOException {
    FileWorker worker = new FileWorker();
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    assertDoesNotThrow(
        () -> worker.writeReportHashToFile("methodReportClass1.txt", methodReportClass1));
  }

  @Test
  void read() throws IOException {
    FileWorker worker = new FileWorker();
    assertDoesNotThrow(() -> worker.readReportHashCode("readTest.txt"));
    int result = worker.readReportHashCode("readTest.txt");
    assertEquals(123, result);
  }

  @Test
  void writeAndReadTest() throws IOException {
    FileWorker worker = new FileWorker();
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    var hash = methodReportClass1.toString().hashCode();
    assertDoesNotThrow(
        () -> worker.writeReportHashToFile("methodReportClass1.txt", methodReportClass1));
    int readHash = worker.readReportHashCode("methodReportClass1.txt");
    assertEquals(hash, readHash);
  }

}
