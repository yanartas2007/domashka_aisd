package dz2;

import java.util.Scanner;

public class postfixCalculator {
    public static void main(String[] args) {
        String pr = "2 5 - 79 + 4 *";
        Scanner sc = new Scanner(System.in);
//        String pr = sc.nextLine();
        DLQueye<String> st = new DLQueye<>();
        int size = 0;
        st.add("");
        for (int i = 0; i < pr.length(); i++) {
            if (pr.charAt(i) == ' ') {
                st.add("");
                size++;
            } else {
               String updatedElement = st.getLast().concat("" + pr.charAt(i));
               st.removeLast();
               st.add(updatedElement);
            }
        }

        for (int i = 0; i <= (size - 1) / 2; i++) {
            int a = fromStringToInt(st.getFirst());
            st.removeFirst();
            int b = fromStringToInt(st.getFirst());
            st.removeFirst();

            int rez = switch (st.getFirst().charAt(0)) {
                case '+' -> a + b;
                case '-' -> a - b;
                case '*' -> a * b;
                default -> 0;
            };
//            System.out.println("" + a + st.getFirst().charAt(0) + b + "=" + rez);
            st.removeFirst();
            st.addFirst("" + rez);
        }

        System.out.println(st.getLast());



    }

    public static int fromStringToInt(String s) {
        int sum = 0;
        int l = s.length();
        boolean subZero = false;

        for (int i = 0; i < l;i++) {
            char c = s.charAt(i);
            if (c == '-') {
                subZero = true;
                continue;
            }
            int d = c - '0';
            int pow = 1;
            for (int j = 0; j < l - i - 1; j++) {
                pow *= 10;
            }
            sum += d * pow;
        }
        return subZero ? -sum : sum;
    }
}
