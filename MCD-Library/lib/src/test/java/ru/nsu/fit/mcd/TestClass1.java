package ru.nsu.fit.mcd;

import java.util.List;

public class TestClass1 {

    private int intField;

    private double doubleField;

    private Object objectField;
    private TestClass3<String[], Double>[] arrayField;

    private List<Integer> list;
    private List<TestClass3<String[], Double>> genericClassList;

    private TestClass2<TestClass2<Integer, Integer>, TestClass2<Double, Double>> genericInsideGenericField;

    /*public int getIntField(int arg){
        return arg;
    }

    public TestClass1 getTestClass1() {
        return this;
    }*/

    public TestClass4 getTestClass4(List<Integer> list)
    {
        return new TestClass4();
    }
}
