package com.company;

import java.util.Random;

public class Main {
    static void printArray(double[][] array) {
        System.out.println();
        System.out.print("    ");
        for (int row = 0; row < array[0].length; row++) {
            System.out.print(row + "        ");
            if (row < 10) {System.out.print(" ");}
        }
        System.out.println();
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (col < 1) {
                    System.out.print(row);
                    if (row < 10) {System.out.print(" ");}
                    System.out.print("  "); System.out.printf("%.5f",array[row][col]);
                }
                else {
                    if (array[row][col-1] >= 0) {System.out.print(" ");}
                    System.out.print("  ");
                    System.out.printf("%.5f",array[row][col]);

                }
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        long[] d = {17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6};
        double[] x = new double[14];
        Random rn = new Random();
        for (int i = 0; i < 14; i++) {
            x[i] = 29.0 * rn.nextDouble() + -14.0;
        }
        double[][] dd = new double[12][14];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 14; j++) {
                switch ((int) d[i]) {
                    case 16:
                        dd[i][j] = Math.log(Math.abs(Math.cos(x[j])));
                        break;
                    case 7:
                    case 8:
                    case 10:
                    case 14:
                    case 15:
                    case 17:
                        dd[i][j] = Math.cos(Math.tan(Math.exp(x[j])));
                        break;
                    default:
                        dd[i][j] = Math.atan(Math.cos(Math.pow(Math.atan((x[j] - 0.5) / 29), 1 / 3)));
                }
            }
        }
        printArray(dd);
    }
}
