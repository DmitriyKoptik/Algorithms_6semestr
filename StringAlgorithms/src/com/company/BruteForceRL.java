package com.company;

import java.io.*;



public class BruteForceRL {
    private String pat;
    private static final String WORD = "ABRACADABRA";
    private static final String TEXT = "ABRACADAABRACADABRAABRACABRDABRA";

    BruteForceRL(String pat) {
        this.pat = pat;
    }

    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--)
                if (pat.charAt(j) != txt.charAt(i + j))
                {
                    skip = 1;
                    break;
                }
            if(skip == 0) {System.out.println("\nобразец найден");
                return i;
            }
        }
        System.out.println("\nобразец не найден в тексте");
        return N;
    }

    public int search(BufferedReader in) throws IOException {
        String txt = in.readLine();
        int N = txt.length();
        int M = pat.length();
        System.out.println("\nТекст в буфере: " + txt);
        for (int i = 0; i <= N - M; i ++) {
            int j;
            for (j = 0; j < M; j++)
                if (pat.charAt(j) != txt.charAt(i + j))
                    break;
            if(j == M) {
                System.out.println("\nобразец найден в тексте в буфере");
                return i;
            }
        }
        System.out.println("\nобразец не найден в тексте в буфере");
        return N;

    }

    public static void main(String[] arg) throws IOException {
        BruteForceRL test = new BruteForceRL(WORD);
        System.out.println("текст:   " + TEXT);
        test.search(TEXT);
        System.out.println("образец: " + WORD);
        BufferedReader in = new BufferedReader(new FileReader("string.txt"), TEXT.length());
        test.search(in);
}
}