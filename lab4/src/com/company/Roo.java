package com.company;

public class Roo extends Time {
    private byte power;
    private byte combo;
    static {
        System.out.println("Roo was created");
    }

    void highJump() {
        if (ExtendMath.checkrandom(power)) {
            System.out.println("Roo did the high jump succesfully");
            combo+=1;
        } else {
            System.out.println("Roo failed the high jump");
            combo=0;
        }
    }
    void longJump() {
        if (ExtendMath.checkrandom(power)) {
            System.out.println("Roo did the long jump succesfully");
            combo+=1;
        } else {
            System.out.println("Roo failed the long jump");
            combo=0;
        }
    }
    void deepJump() {
        if (ExtendMath.checkrandom(power)) {
            System.out.println("Roo did the deep jump succesfully");
            combo+=1;
        } else {
            System.out.println("Roo failed the deep jump");
            combo=0;
        }
    }
    void updateStatus() {
        if (updateStatus) {
            switch (daypart) {
                case NIGHT: setPower(25); System.out.println("At Night, Roo really tired"); break;
                case MORNING: setPower(100); System.out.println("In the Morning, Roo at full of power"); break;
                case AFTERNOON: setPower(75); System.out.println("In the Afternoon, Roo can jump well"); break;
                case EVENING: setPower(50); System.out.println("In the Evening, Roo at half of power"); break;
            }
        }
    }
    void setPower(int p) {
        power = (byte)ExtendMath.clamp(p,0,100);
    }
    public class Kanga {
        {
            System.out.println("Ranga was created");
        }
        void communicate() {
            if (combo>1) {
                System.out.println("Kanga congratulated Roo");
            }
            else {
                System.out.println("Kanga was worried and told Roo to go home");
            }
        }
    }
}
