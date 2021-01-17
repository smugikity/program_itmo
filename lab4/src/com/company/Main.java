package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Roo roo = new Roo();
	    Everybody everybody = new Everybody();
	    Roo.Kanga kanga = roo.new Kanga();
	    Everybody.Pooh pooh = new Everybody.Pooh();
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Set the initial time (0-23): ");
        CheckInput check = new CheckInput() {
            @Override
            public void checkInput(int t) {
                try {
                    if (t > 23 || t < 0) {
                        throw new OverrangedException(t);
                    } else {
                        roo.initialTime((byte) t);
                    }
                } catch (OverrangedException ex) {
                    ex.printStackTrace();
                }
            }

        };
        check.checkInput(keyboard.nextInt());
        try {
            if (Time.daypart==null||Time.olddaypart==null) {
                throw new NullVariableException();
            } else {
                while (!Everybody.searched) {
                    roo.updateStatus();
                    roo.deepJump();
                    roo.highJump();
                    roo.longJump();
                    kanga.communicate();
                    everybody.updateStatus();
                    pooh.act();
                    roo.updateTime();
                    Thread.sleep(1000);
                }
            }
            System.out.println("yo");
        }
        catch (NullVariableException ex) {
            ex.getStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
