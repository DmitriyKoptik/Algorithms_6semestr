package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class BoyerMoore {
    private int[] right;
    private String pat;
    private static final String WORD = "ABRACADABRA";
    private static final String TEXT = "ABRACADAABRACADABRAABRACABRDABRA";

    BoyerMoore(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];
        for(int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < M; j++)
            right[pat.charAt(j)] = j;
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
                    skip = j - right[txt.charAt(i + j)];
                    if (skip < 1) skip = 1;
                    break;
                }
            if(skip == 0)  return i;
        }
        return N;
    }

    public void printRightArrayContent(){
        System.out.println("right array: " + Arrays.toString(right));
    }

    public static void main(String[] arg) {
        BoyerMoore test = new BoyerMoore(WORD);
        System.out.println("текст:   " + TEXT);
        test.search(TEXT);
        System.out.println("образец: " + WORD);
        test.printRightArrayContent();
    }
}
