package dz8;

import java.util.*;

public class Task2 {
    public static void main(String[] args) {
        int[] n = new int[]{1,2,3,2,1,2,3,4};
        int[] m = new int[n.length]; // для каждого числа храним количество больших его справа. идем справа налево, используем имеющиеся данные дальше
        for (int i = n.length - 1; i >= 0; i--) {
            int maxLocal = 1;
            for (int j = i + 1; j < n.length; j++) {
                if (n[j] > n[i]) {
                    if (m[j] + 1 > maxLocal) {
                        maxLocal = m[j] + 1;
                    }
                }
            }
            m[i] = maxLocal;
        }
        System.out.println(Arrays.stream(m).max().stream().toArray()[0]);
    }
}
