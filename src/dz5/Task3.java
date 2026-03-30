package dz5;

public class Task3 {
    public static void main(String[] args) {
        int[] array = {2, 5,7, 11, 15};
        int target = 9;

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int sum = array[left] + array[right];
            if (sum == target) {
                System.out.println(array[left] + " " + array[right]);
                return;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println("нету таких");
    }
}