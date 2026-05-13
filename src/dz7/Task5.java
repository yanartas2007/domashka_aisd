package dz7;

import java.util.*;

public class Task5 {
    public static void main(String[] args) {
        int[] gvozdi = new int[]{0, 10, 11, 12, 13, 20};

        Arrays.sort(gvozdi);
        int n = gvozdi.length;

        int ans = 0;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (i == 0) { // очевидно, первый соединен со вторым
                ans += gvozdi[1] - gvozdi[0];
                flag = true;
            }
            else if (i == n - 2) { // очевидно, последний соединен с предпоследним
                ans += gvozdi[n-1] - gvozdi[n-2];
                break;
            }
            else if (flag) { // то есть если уже присоединен слева
                flag = false;
                continue;
            }
            else {
                if (gvozdi[i + 1] - gvozdi[i] <= gvozdi[i] - gvozdi[i-1]) { // вот тут именно <= а не <, так как если одинаково лучше брать справа, тот который слева уже с кеи то соединен
                    flag = true;
                    ans += gvozdi[i + 1] - gvozdi[i];
                }
                else {
                    ans +=  gvozdi[i] - gvozdi[i-1];
                }
            }

        }
        System.out.println(ans);
    }
    }
