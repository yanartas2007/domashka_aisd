package dz7;

public class Task2 {
    public static void main(String[] args) {
        int n = 7 % 60; // я нагуглил что последняя цифра повторяется с периодом 60. таким образом, задача решается за констанную сложность по времени
        int first = 1;
        int second = 1;
        int swap = 1;
        for (int i = 0; i < n - 2; i++) {
            swap = (first + second) % 10;
            first = second;
            second = swap;
        }
        System.out.println(swap);

    }
}
