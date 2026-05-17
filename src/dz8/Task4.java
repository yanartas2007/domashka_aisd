package dz8;

import java.util.HashMap;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        HashMap<Long, Long> left = new HashMap<>();
        HashMap<Long, Long> right = new HashMap<>();

        // суть в том, что мы идем по массиву, рассматривая средний элемент. для каждого у нас есть список справа и слева. используем списки, чтобы найти подходящие значения

        for (long i : a) {
            right.put(i, right.getOrDefault(i, 0L) + 1);
        }

        long ans = 0;

        for (int i = 0; i < n; i++) {
            long now = a[i];
            long cnt = right.get(now);
            if (cnt == 1) {
                right.remove(now);
            } else {
                right.put(now, cnt - 1);
            }

            if (now % k == 0) {
                long first = now / k;
                long tretiy = now * k;
                long left1 = left.getOrDefault(first, 0L);
                long right1 = right.getOrDefault(tretiy, 0L);
                ans += left1 * right1;
            }

            left.put(now, left.getOrDefault(now, 0L) + 1);
        }

        System.out.println(ans);
    }
}