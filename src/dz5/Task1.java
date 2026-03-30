package dz5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Task1 {
    public static void main(String[] args) {
        int[] array = {2, 7, 11, 15};
        int target = 9;

        HashSet<Integer> hm = new HashSet<>();

        for (int i = 0; i < array.length; i++) {
            int last_part_of_target = target - array[i];
            if (hm.contains(last_part_of_target)) {
                System.out.println(last_part_of_target + " " + array[i]);
                return;
            }
            hm.add(array[i]);
        }

        System.out.println("нету таких");
    }
    }
