package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the Wave level (0 -> 100):");
        Subject.setWaveLevel(keyboard.nextInt());
        ThreadBox box = new ThreadBox();
        box.Float();
        box.Sway();
        Mu mu = new Mu();
        mu.Awake();
        mu.hook.Clung(box);
        Snusmumrik snus = new Snusmumrik();
        snus.Act(box);
    }
}
