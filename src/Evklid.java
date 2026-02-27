import java.util.Scanner;

public class Evklid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();

        if (a == 0 || b == 0) {
            System.out.println(0);
            return;
        }

        if (a > b) {
            int swap = a;
            a = b;
            b = swap;
        }

        while (a % b != 0) {
            int ost = a % b;
            a = b;
            b = ost;
        }

        System.out.println(b);

    }
}

