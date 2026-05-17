package dz8;

public class Task3 {
    public static void main(String[] args) {
        int[][] n = new int[][]{{0,0,1,1},
                {0,0,0,0},
                {1,0,0,1},
                {1,0,1,0}};
        int dorogDo = 0;
        for (int[] i: n) {
            for (int j: i) {
                if (j == 1) {
                    dorogDo += 2;
                }
            }
        }
        System.out.println(dorogDo - (dorogDo / 2 + 1)); // теорема о минимальном остовном графе позволяет найти минимальный остов (логично). потом вычитаем из числа дорог минимальный остов, получаем сколько убрали
    }
}
