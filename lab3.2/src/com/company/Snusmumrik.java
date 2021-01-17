package com.company;

public class Snusmumrik extends Subject {
    public Snusmumrik(MoominFamily moominfamily) {
        this.setName("Snusmumrik");
        System.out.println(getName()+" was created");
        if (!ExtendMath.checkrandom(getWaveLevel())) {
            System.out.println("Accidents and coincidences work wonders. Knowing nothing about each other and about each other's adventures");
            System.out.println(getName()+" and "+moominfamily.getName()+Action.BE.getDes()+Position.BAY.getDes()+Adverb.EVENING.getDes());
        } else {
            System.out.println(moominfamily.getName()+" didn't go to the bay");
        }
    }
    void Act(ThreadBox box) {
        if (!ExtendMath.checkrandom(getWaveLevel())) {
            System.out.println(Move(Action.STAND, Position.BANK, Adverb.GREENHAT));
            if (box.getCurrentPosition() != Position.NONAVAILABLE) {
                System.out.println(Move(Action.STARE, Position.BOX));
            }
        } else {
            System.out.println("The storm made "+Move(Action.RANINTO, Position.HOUSE));
        }
    }
}
