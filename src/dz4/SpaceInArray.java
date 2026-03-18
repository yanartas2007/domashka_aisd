package dz4;

public class SpaceInArray {
    public static void main(String[] args) {
        int[] digits = {0, 1, 2, 3, 5, 6, 4, 8, 9}; // 7
        int sum = -1; // помимо суммы, это еще и flag для 0.
        for (int i: digits) {
            if (i == 0) {
                sum = 0;
            }
        }
        if (sum == -1) {
            System.out.println(0);
        }
        else {
            for (int i: digits) {
                sum += i;
            }
            System.out.println((int) (((double) digits.length + 1) / 2) * digits.length - sum);
        }
    }
}
