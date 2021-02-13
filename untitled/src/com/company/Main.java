package com.company;

public class Main {
public class Clazz {
    public static void method(){
        System.out.println("kill me");
    }
}

    public static void main(String[] args) {
    Object obj = null;
        ((Clazz)obj).method();
    }
}
