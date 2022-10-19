package ru.nsu.fit.mcd;

import java.util.List;

public class TestClass1 {

    public int intField;

    public double doubleField;

    public Object objectField;
    public TestClass3<String[], Double>[] arrayField;

    public List<Integer> list;
    public List<TestClass3<String[], Double>> genericClassList;

    public TestClass2<TestClass2<Integer, Integer>, TestClass2<Double, Double>> genericInsideGenericField;
}
