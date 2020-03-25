package com.calc.utils;

/**
 * Enum for operations
 */
public enum Operations {
    ADDITION("addition"),
    DIVISION("division"),
    MULTIPLICATION("multiplication"),
    SUBTRACTION("subtraction");

    private String operation;

    Operations(String operation) { this.operation = operation; }
}
