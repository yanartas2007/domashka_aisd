package dz4;

public class CyclicSdvig {
    public static void main(String[] args) {
        String a = "кабан";
        String b = "банка";
        String c = "нечто неописуемое";
        String d = "кбаан";

        System.out.println(isSdvig(a, b)); // t
        System.out.println(isSdvig(b, a)); // t
        System.out.println(isSdvig(a, a)); // t
        System.out.println(isSdvig(a, c)); // f
        System.out.println(isSdvig(a, d)); // f
    }

    public static boolean isSdvig(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false; // не уверен насчет этого. считается ли большая строка сдвигом меньшей и наоборот? так что пусть будет для строк одной длины
        }

        String s1s1 = s1 + s1;
        for (int i = 0; i < s1.length(); i++) {
            if (s1s1.charAt(i) == s2.charAt(0)) {
                for (int j = 0; j < i + s1.length(); j++) {
                    if (s1s1.charAt(i + j) != s2.charAt(j)) {
                        break;
                    }
                    if (j == s1.length() - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
