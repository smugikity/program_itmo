package com.company;

public class OverrangedException extends Exception {
    public OverrangedException(int t) {
        super(t+" is not a valid time");
    }
}
