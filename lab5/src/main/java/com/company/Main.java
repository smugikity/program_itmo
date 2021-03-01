package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Source XML file (lab5.xml): "+System.getProperty("user.dir"));
        //"E:\\bai_tap\\program\\lab5\\src\\com\\company\\lab5.xml"
        Reader reader = new Reader(System.getProperty("user.dir")+new Scanner(System.in).nextLine(), new CommandReader());
    }
}
