package dz7;

public class Task4 {
    public static void main(String[] args) {
        int N = 3; // ширина
        int M = 3; // высота
        int[][] nm = new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        int[][] minsum = new int[N][M];
        minsum[0][0] = nm[0][0];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (x == 0 && y == 0) continue;
                if (x == 0) {
                    minsum[y][x] = nm[y][x] + minsum[y-1][x];
                }
                else if (y == 0) {
                    minsum[y][x] = nm[y][x] + minsum[y][x-1];
                }
                else {
                    minsum[y][x] = nm[y][x] + minsum[y-1][x] < nm[y][x] + minsum[y][x-1] ? nm[y][x] + minsum[y-1][x] : nm[y][x] + minsum[y][x-1];
                }
            }
        }
        System.out.println(minsum[M-1][N-1]);
    }
}
