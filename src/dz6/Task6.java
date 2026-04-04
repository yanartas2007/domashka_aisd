package dz6;

import java.util.HashSet;
import java.util.Set;

public class Task6 {
    public static Integer findRepeatingElement(int[] arr1, int[] arr2, int[] arr3) {
        Set<Integer> in1 = new HashSet<>();
        for (int num : arr1) {
            in1.add(num);
        }

        Set<Integer> in1and2 = new HashSet<>();
        for (int num : arr2) {
            if (in1.contains(num)) {
                in1and2.add(num);
            }
        }

        for (int num : arr3) {
            if (in1and2.contains(num)) {
                return num;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        int[] b = {2, 4, 6, 8};
        int[] c = {2, 5, 7, 9};
        Integer result = findRepeatingElement(a, b, c);
        System.out.println(result);
    }
}