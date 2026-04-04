package dz6;

import java.util.Scanner;

import static java.lang.Math.abs;

public class Task2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.println(square(a) == b);
    }

    private static int square(int a) { // сомневаюсь, что это быстрее чем a * a, но не знаю, как еще сделать через побитовые операции.
        int x = abs(a);
        int y = abs(a);
        int result = 0;
        while (y != 0) {
            if ((y & 1) != 0) {
                result += x;
            }
            x <<= 1;
            y >>= 1;
        }
        return result;
    }
}
