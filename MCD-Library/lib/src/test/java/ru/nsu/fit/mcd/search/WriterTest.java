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
    var hash = (long) methodReportClass1.toString().hashCode();
    assertDoesNotThrow(
        () -> worker.writeToFile("src/test/methodReportClass1.txt", String.valueOf(hash)));
  }

  @Test
  void read() throws IOException {
    FileWorker worker = new FileWorker();
    assertDoesNotThrow(() -> worker.readReportHashCode("src/test/readTest.txt"));
    int result = worker.readReportHashCode("src/test/readTest.txt");
    assertEquals(123, result);
  }

  @Test
  void writeAndReadTest() throws IOException {
    FileWorker worker = new FileWorker();
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    var hash = methodReportClass1.toString().hashCode();

    assertDoesNotThrow(
        () -> worker.writeToFile("src/test/methodReportClass1.txt", String.valueOf(hash)));
    int readHash = worker.readReportHashCode("src/test/methodReportClass1.txt");
    assertEquals(hash, readHash);
  }

}
