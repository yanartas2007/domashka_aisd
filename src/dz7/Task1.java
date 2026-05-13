package dz7;

public class Task1 {
    public static void main(String[] args) {
        int a = 10946;

        int first = 1;
        int second = 1;
        int swap = 0;
        while (swap < a) {
            swap = first + second;
            first = second;
            second = swap;
        }
        System.out.println(a == swap);
    }
}
