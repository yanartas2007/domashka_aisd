package dz6;

public class Task4 {
    public static void main(String[] args) {
        Tree<Integer> t = new Tree<>();
        t.add(5);
        t.add(3);
        t.add(7);
        t.add(25);
        recursiveCheck(t.getRoot());
    }

    public static int recursiveCheck(TreeNode<Integer> tn) { // выводятся суммы снизу вверх
        if (tn.getLeft() != null && tn.getRight() != null) {
            int a = recursiveCheck(tn.getLeft()) + recursiveCheck(tn.getRight()) + tn.getValue();
            System.out.println(a);
            return a;
        }
        if (tn.getLeft() != null) {
            int a = recursiveCheck(tn.getLeft()) + tn.getValue();
            System.out.println(a);
            return a;
        }
        if (tn.getRight() != null) {
            int a = recursiveCheck(tn.getRight()) + tn.getValue();
            System.out.println(a);
            return a;
        }
        int a = tn.getValue();
        System.out.println(a);
        return a;
    }
}
