package ru.nsu.fit.mcd.user;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Mcd {
    String getMethodHash(Class target, String methodName);

    String getObjectHash(Class target) throws JsonProcessingException;
}
