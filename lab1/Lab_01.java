package com.company;
import java.util.Random;
import java.lang.Math;

public class Lab_01 {
    public static void main(String[] args) {
        short[] c = new short[(24 - 2) / 2 + 1];    //Создать массив с
        int n = 0;
        for (short i = 2; i <= 24; i +=2) {
            c[n] = i;    //c[0..12] = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24}
            n++;
        }
        float[] x = new float[10];    //Создать массив x
        for (int i = 0; i <= 17; i++) {
            Random rd = new Random();    //Создание объект класса Random
            x[i] = rd.nextFloat() * 12 - 8;    //Использование метода nextFloat()
        }
        double[][] a = new double[12][10];    //Создать двумерный массив a
        for (int i = 0; i <= 12; i++) {
            for (int j = 0; j <= 10; j ++) {
                switch(c[i]) {
                    case 20:
                        a[i][j] = Math.log(Math.pow(Math.tan(Math.cos(Math.pow(x[j] + 1.0, x[j]))), 2));
                        break;

                    case 2:
                    case 6:
                    case 8:
                    case 14:
                    case 16:
                    case 18:
                        a[i][j] = Math.sin(Math.pow((Math.PI / x[j] ) / 0.25, Math.pow(Math.E, x[j])));
                        break;

                    default:
                        double index1 = (Math.pow(2*Math.PI*Math.abs(x[j]), 2)) / 2;
                        a[i][j] = Math.log(Math.pow(index1, Math.asin(1 / Math.pow(Math.E, Math.abs(x[j])))));
                        break;
                }
                System.out.printf("%6.3f", a[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }

    }

}
