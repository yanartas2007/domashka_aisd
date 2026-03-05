package dz2;

public class MergeTwoSortedQueues {
    public static void main(String[] args) {
        DLQueye<Integer> a = new DLQueye<>();
        DLQueye<Integer> b = new DLQueye<>();
        int[] m1 = new int[] {1,7,9,11,13};
        int[] m2 = new int[] {2, 3, 4, 14, 15, 116};
        for (int i: m1) {
            a.add(i);
        }
        for (int i: m2) {
            b.add(i);
        }


        DLQueye<Integer> c = new DLQueye<>();
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
        while (!a.isEmpty()) {
            c.add(a.pop());
        }
        while (!b.isEmpty()) {
            c.add(b.pop());
        }
        while (!c.isEmpty()) {
            System.out.print(c.pop() + " ");
        }
    }
}
