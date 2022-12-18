/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.mcd.search;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.mcd.TestClass1;
import ru.nsu.fit.mcd.TestClass2;
import ru.nsu.fit.mcd.TestClass3;
import ru.nsu.fit.mcd.TestClass4;

class LibraryTest {

  private final TestClass2<Long, Integer> testClass = new TestClass2<>();
  private final TestClass1 testClass1 = new TestClass1();

  @Test
  void testClassScan() throws IOException {
    System.out.println(ClassSearchCore.getClassReport(TestClass4.class));
  }

  @Test
  void test() {
    Arrays.stream(LibraryTest.class.getDeclaredFields()).forEach(
        field -> System.out.println(
            Arrays.stream(List.of(field.getGenericType()).toArray()).collect(Collectors.toList()))
    );
  }

  @Test
  void methodTest() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    var methodReport = MethodSearchCore.getMethodsReport(TestClass1.class);
    System.out.println(methodReport);
  }

  @Test
  void methodHashEqualTest() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    var methodReport = MethodSearchCore.getMethodsReport(TestClass1.class);
    for (int i = 0; i < 10000; i++) {
      var hash1 = methodReport.toString().hashCode();
      var hash2 = methodReport.toString().hashCode();
      assertEquals(hash1, hash2);
    }
    System.out.println(methodReport);
  }

  @Test
  void methodHashNotEqualsTest() throws IOException {
    var methodReportClass1 = MethodSearchCore.getMethodsReport(TestClass1.class);
    var methodReportClass3 = MethodSearchCore.getMethodsReport(TestClass3.class);
    var hashClass1 = methodReportClass1.toString().hashCode();
    var hashClass2 = methodReportClass3.toString().hashCode();
    assertNotEquals(hashClass1, hashClass2);
  }
}
