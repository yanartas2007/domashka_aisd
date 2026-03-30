package dz5;

import java.util.Arrays;
import java.util.Comparator;

public class Task4 {
    public static void main(String[] args) {
        int[] nums1 = {1, 4, 7, 2};
        System.out.println(createMaxNumber(nums1)); // 7421

        int[] nums2 = {3, 30, 34, 5, 9};
        System.out.println(createMaxNumber(nums2)); // 9534330

        int[] nums3 = {10, 2};
        System.out.println(createMaxNumber(nums3)); // 210
    }

    public static String createMaxNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }

        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                String combinationA = a + b;
                String combinationB = b + a;
                return combinationB.compareTo(combinationA);
            }
        });

        if (strings[0].equals("0")) { // мало ли там только 0
            return "0";
        }

        String ans = "";
        for (String s : strings) {
            ans = ans + s;
        }

        return ans;
    }
}