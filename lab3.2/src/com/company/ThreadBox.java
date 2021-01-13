package com.company;

public class ThreadBox extends Subject{
    {
        this.setName("the thread box");
        System.out.println(getName()+" was created");
    }

    void Float() {
        System.out.println(Move(Action.FLOAT,((getWaveLevel()<=50)?Adverb.GENTLY:Adverb.STRONGLY)));
        System.out.println(Move(Action.CARRIED,Position.BAY)+" where located"+Position.HOUSE.getDes());
    }
    void Sway() {
        if (!ExtendMath.checkrandom(getWaveLevel())) {
            System.out.println(Move(Action.SWAY,Position.COASTALREEDS));
            System.out.println(Move(Action.BOGGED,Position.MUD));
        } else {
            System.out.println("the storm made "+Move(Action.BREAKTHROUGH,Position.COASTALREEDS));
        }
    }
}
