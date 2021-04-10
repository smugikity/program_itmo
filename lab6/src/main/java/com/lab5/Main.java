package com.lab5;

public class Main {
    public static void main(String[] args) {
        try {
            //System.out.print("Source XML file (lab5.xml): " + System.getProperty("user.dir"));
            //"E:\\bai_tap\\program\\lab5\\src\\com\\company\\lab5.xml"
            Reader reader = new Reader(args[0].trim(), new CommandReader());//System.getProperty("user.dir")+new Scanner(System.in).nextLine()
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("No such file exist");
        }
    }
}
