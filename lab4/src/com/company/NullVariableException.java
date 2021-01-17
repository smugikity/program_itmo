package com.company;

public class NullVariableException extends Exception{
    public NullVariableException() {
        super("Variable daypart or olddaypart is null");
    }
}
