package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the Wave level (0 -> 100): ");
        Subject.setWaveLevel(keyboard.nextInt());
        ThreadBox box = new ThreadBox();
        box.Float();
        box.Sway(); 
        FishHook fishhook = new FishHook();
        Mu mu = new Mu();
        mu.Awake(fishhook);
        fishhook.Clung(box);
        MoominFamily moominfamily = new MoominFamily();
        Snusmumrik snus = new Snusmumrik(moominfamily);
        snus.Act(box);
    }
}
