package com.company;

public class FishHook extends Subject {
    {
        setName("the fish hook");
        System.out.println(getName()+" was created");
    }
    void Clung(ThreadBox box) {
        if (box.getCurrentPosition()==Position.MUD) {
            System.out.println(Move(Action.CLUNG,box));
            if (!ExtendMath.checkrandom(getWaveLevel())) {
                System.out.println(Move(Action.TWITCH,Adverb.ONCEORTWICE)
                        +", the line pulled tight "+box.Move(Action.FLOAT,Position.MUD,Adverb.CAREFULLY));
            } else {
                System.out.println(Move(Action.BROKEN)+" and "+box.Move(Action.BLOWNAWAY));
            }
        } else {
            if (!ExtendMath.checkrandom(getWaveLevel())) {
                System.out.println(Move(Action.BOGGED, Position.MUD));
            } else {
                System.out.println(Move(Action.BLOWNAWAY));
            }
        }
    }
}
