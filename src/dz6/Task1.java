package dz6;

public class Task1 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,5,7,9};
        int target = 6;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > target) {
                System.out.println(i-1);
                return;
            }
        }
        System.out.println(arr.length-1);
    }
}
