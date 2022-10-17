package ru.nsu.fit.mcd.user;

public interface Mcd {
    String getMethodHash(Class target, String methodName);

    String getObjectHash(Class target);
}
