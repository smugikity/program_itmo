package com.company;

public class Everybody extends Time {
    static {
        System.out.println("Everybody was created");
    }
    private String[] places = {"Pushkin","Senaya","Zhelakin","Polenezh","Lykarino","Kaspiretsk","Borisoluga","Myshinyye Nory"};
    static boolean searched = false;
    void updateStatus() {
        if (getTime()==23||getTime()==5||getTime()==12||getTime()==17) {
            switch (daypart) {
                case NIGHT: searching(1); break;
                case MORNING: searching(4); break;
                case AFTERNOON: searching(3); break;
                case EVENING: searching(2); break;
            }
        }
    }
    void searching(int productivity) {
        String[] searchedPlaces = new String[productivity];
        System.out.print("Last "+olddaypart.getDes()+" everybody searched ");
        for (int i=0;i<productivity;i++) {
            int indexPlace = ExtendMath.rangeRandom(0,places.length-1);
            searchedPlaces[i]=places[indexPlace];
            places = removeTheElement(places,indexPlace);
            if (searchedPlaces[i].equals("Myshinyye Nory")) {searched = true;}
            System.out.print(searchedPlaces[i]+((i+1==productivity)?"":" and "));
        }
        if (searched) {System.out.println(". Finally, they found Roo at Myshinyye Nory");}
        else {System.out.println("but still didnt find Roo yet");}
    }
    static class Pooh {
        static {
            System.out.println("Pooh was created");
        }
        void act() {
            if (searched) {
                System.out.println("Pooh appeared on the hill");
                System.exit(0);
            } else {
                System.out.println("Pooh was searching for Roo with everybody else");
            }
        }
    }

    public static String[] removeTheElement(String[] arr, int index)
    {
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        String[] anotherArray = new String[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }
}
