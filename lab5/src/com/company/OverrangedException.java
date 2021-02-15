package com.company;

public class OverrangedException extends Exception {
    public OverrangedException() {
        super("Parameter need to be larger than 0");
    }
}