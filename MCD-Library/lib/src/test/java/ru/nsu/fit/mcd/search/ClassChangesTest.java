package ru.nsu.fit.mcd.search;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.mcd.TestClass1;
import ru.nsu.fit.mcd.TestClass2;

public class ClassChangesTest {

  @Test
  void writeAndReadEqualsTest() throws IOException {
    FileWorker worker = new FileWorker();
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    var hash = methodReportClass1.hashCode();
    assertDoesNotThrow(
        () -> worker.writeToFile(
            "src/test/classMethodsHash/" + methodReportClass1.getClazzName() + "MethodReportHash" + ".txt",
            String.valueOf(hash)));
    int readHash = worker.readReportHashCode(
        "src/test/classMethodsHash/" + methodReportClass1.getClazzName() + "MethodReportHash.txt");
    assertEquals(hash, readHash);
  }

  @Test
  void writeAndReadNotEqualTest() throws IOException {
    FileWorker worker = new FileWorker();
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    var hash = methodReportClass1.hashCode();
    assertDoesNotThrow(
        () -> worker.writeToFile(
            "src/test/classMethodsHash/" + methodReportClass1.getClazzName() + "MethodReportHash" + ".txt",
            String.valueOf(hash)));
    var methodReportClass2 = MethodSearchCore.getMethodsReport(TestClass2.class);
    var hash2 = methodReportClass2.hashCode();
    int readHash = worker.readReportHashCode(
        "src/test/classMethodsHash/ru.nsu.fit.mcd.TestClass1MethodReportHash.txt");
    assertNotEquals(hash2, readHash);
  }
}
