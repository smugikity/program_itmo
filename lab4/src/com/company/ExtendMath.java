package com.company;

public interface ExtendMath {
    static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
    static boolean checkrandom(int value){
        return Math.random()*100 <= value;
    }
    static int rangeRandom(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
}
