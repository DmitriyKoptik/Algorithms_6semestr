package com.company;

import java.util.Random;

/*
    5 ВАРИАНТ
    УСЛОВИЕ: Задано конечное множество А, "размер" s(a)⊆Z+ для каждого а⊆А и положительные целые числа K и J;
    ВОПРОС: Могут ли элементы из А быть разбиты на К непересекающихся множеств А1,А2,...,Ак так, что ∑[i=1;K](∑[a⊆Ai]s(a))^2<=J?
*/

public class Main {
    public static void main(String[] args) {
        int b = 4;
        int j = (b*b)/2;
        int s[] = new int[16];
        int i;
        for (int k = 1; k < 16; k++) {
            for (i = 0; i < k; i++) {
                Random rn = new Random();
                s[i] = rn.nextInt(9)+1;
            }
            System.out.println("Сумма равна " + calculateSquareSum(s) + " -> " + findMinSquareSum(i, j));
            System.out.println("----------------------");
        }
    }

    public static int calculateSquareSum(int[] s) {
        int sum = 0;
        for (int i = 0; i < s.length; i++) {
            sum = sum + (s[i]*s[i]);
        }
        return sum;
    }

    public static String findMinSquareSum(int i, int j) {
        return (i <= j) ? "Могут быть разбиты" : "Не могут быть разбиты";
    }
}
