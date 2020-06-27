package com.company;

/*
    6 ВАРИАНТ
    УСЛОВИЕ: Задан ориентированный граф G = (V, А) и положительное целое число K, K ≤ |V|;
    ВОПРОС: Существует ли такое подмножество V' ⊆ V, |V'| ≤ K и любой ориетированный цикл в G содержит по крайней мере одно ребро из V'?
*/

/*
    Например |V| = 5
    V' ⊆ V       --> v  = {0, 1, 2, 3, 4, 5} вершин
    K  ≤ |V|     --> k  = {0, 1, 2, 3, 4, 5}
*/

public class Main {
    public static void main(String[] args) {
        int n = 5;
        for (int v = 0; v <= n; v++) {
            for (int k = 0; k <= n; k++) {
                System.out.println("V' = " + v + ", K = " + k + " -> " + isExistSubset(v, k));
            }
            System.out.println("----------------------");
        }
    }

    public static String isExistSubset(int v, int k) {
        return (v <= k) && (v > 0) ? "Существует" : "Не существует";
    }
}
