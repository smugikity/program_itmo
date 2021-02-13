package com.company;

public abstract class Time {
    static int time = 100;
    static Daypart daypart=null;
    static Daypart olddaypart=null;
    static boolean updateStatus = false;
    void initialTime(byte t) {
        time = t;
        setDaypart();
        olddaypart = daypart;
        informTime();
    }
    void updateTime() {
        time += 1;
        olddaypart = daypart;
        setDaypart();
        updateStatus = daypart != olddaypart;
        informTime();
    }
    void setDaypart() {
        switch (getTime()) {
            case 6: case 7: case 8: case 9: case 10: case 11: daypart = Daypart.MORNING; break;
            case 12: case 13: case 14: case 15: case 16: case 17: daypart = Daypart.AFTERNOON; break;
            case 18: case 19: case 20: case 21: case 23: daypart = Daypart.EVENING; break;
            case 0: case 1: case 2: case 3: case 4: case 5: daypart = Daypart.NIGHT; break;
        }
    }

    int getTime() {
        return time % 24;
    }
    void informTime() {
        int t = getTime();
        switch (t) {
            case 6: System.out.println("6 A.M. Sunrise"); break;
            case 12: System.out.println("12 A.M. Noon"); break;
            case 18: System.out.println("6 P.M. Sunset"); break;
            case 0: System.out.println("12 P.M. Midnight"); break;
            default: System.out.println(((t<13)?(t+" A.M. "):(t-12+" P.M. "))+daypart.getDes()); break;
        }
    }

}
