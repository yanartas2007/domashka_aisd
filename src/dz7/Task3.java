package dz7;

public class Task3 {
    public static void main(String[] args) {
        String s = "abcdefghij";
        int first = 1;
        int second = 1;
        int swap = 1;
        System.out.print(s.charAt(0));
        while (first + second < s.length()) {
            swap = first + second;
            first = second;
            second = swap;
            System.out.print(s.charAt(swap - 1));
        }
    }
}
