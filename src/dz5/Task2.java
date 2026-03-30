package dz5;

import java.util.Arrays;
import java.util.Comparator;

public class Task2 {
    public static void main(String[] args) {
        String[] strings = {"nuggets", "Nuggets", "apple", "banana", "appple", "Apple"};

        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        });

        for (String s: strings) {
            System.out.print(s + "   ");
        }
    }
}