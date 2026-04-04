package dz6;

import java.util.ArrayList;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(sc1.nextLine());
        ArrayList<String> words = new ArrayList<>();
        while(sc2.hasNext()) {
            words.add(sc2.next());
        }
        for (int i = words.size()-1;i>=0;i--) {
            System.out.println(words.get(i));
        }
    }
}
