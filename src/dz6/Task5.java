package dz6;

import java.util.Arrays;

public class Task5 {

    public static void main(String[] args) {
        int[] nums = {-1, 2, 1, -4};
        int target = 1;

        Arrays.sort(nums);
        int n = nums.length;
        int closestSum = nums[0] + nums[1] + nums[2];
        int[] closests = new int[3];
        for (int i = 0; i < n - 2; i++) { // это O(n^2). я перепробовал несколько способов и не добился O(n) как требуется. мне кажется, что это невозможно в данной задаче. спросил у дипсика, он подтверждает, что O(n) не достичь
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                if (currentSum == target) {
                    System.out.print(nums[i] + " " + nums[left] + " " + nums[right]);
                    return;
                }
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closests[0] = nums[i];
                    closests[1] = nums[left];
                    closests[2] = nums[right];
                    closestSum = currentSum;
                }
                if (currentSum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        System.out.println(Arrays.toString(closests));
    }
}
