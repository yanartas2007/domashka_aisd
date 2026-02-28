import java.util.ArrayDeque;
import java.util.Queue;

public class MergeTwoSortedQueues {
    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        ArrayDeque<Integer> b = new ArrayDeque<>();
        int[] m1 = new int[] {1,7,9,11,13};
        int[] m2 = new int[] {2, 3, 4, 14, 15, 116};
        for (int i: m1) {
            a.add(i);
        }
        for (int i: m2) {
            b.add(i);
        }


        ArrayDeque<Integer> c = new ArrayDeque<>();
        int i = a.pop();
        int j = b.pop();
        while (true) {
            if (i > j) {
                c.add(j);
                if (b.isEmpty()) {break;}
                j = b.pop();
            }
            else {
                c.add(i);
                if (a.isEmpty()) {break;}
                i = a.pop();
            }
        }
        for (int k: a) {
            c.add(k);
        }
        for (int k: b) {
            c.add(k);
        }

        for (int k: c) {
            System.out.print(k + " ");
        }
    }
}
