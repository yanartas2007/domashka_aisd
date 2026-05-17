package dz8;

import java.util.HashSet;

public class Task1 {
    public static void main(String[] args) {
        int N = 3; // ширина
        int M = 3; // высота
        int[][] nm = new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        HashSet<Integer>[][] minsum = new HashSet[N][M];
        minsum[0][0] = new HashSet<>();
        minsum[0][0].add(nm[0][0]);
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (x == 0 && y == 0) continue;
                minsum[y][x] = new HashSet<>();
                if (x == 0) {
                    for (int i: minsum[y-1][x]){
                        minsum[y][x].add(i + nm[y][x]);
                    }
                }
                else if (y == 0) {
                    for (int i: minsum[y][x-1]) {
                        minsum[y][x].add(nm[y][x] + i);
                    }
                }
                else {
                    for (int i: minsum[y-1][x]){
                        minsum[y][x].add(i + nm[y][x]);
                    }
                    for (int i: minsum[y][x-1]) {
                        minsum[y][x].add(nm[y][x] + i);
                    }
                }
            }
        }
        System.out.println(minsum[M-1][N-1].size());
    }
}
