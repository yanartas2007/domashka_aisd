package dz3;

public class Test {
    public static void main(String[] args) {
        Tree<Double> tree = new Tree<>();
        tree.add(5.0);
        if (tree.getHeight() != 1) {System.out.println("!" + 1);}
        tree.add(6.0);
        tree.add(7.5);
        tree.add(5.5);
        if (tree.getHeight() != 3) {System.out.println("!" + 2);}

        System.out.println(tree.getRoot().getValue());
        System.out.println(tree.getRoot().getRight().getValue());
        System.out.println(tree.getRoot().getRight().getRight().getValue());
        System.out.println(tree.getRoot().getRight().getLeft().getValue());

//        5.0                        l1
//           6.0                     l2
//        5.5   7.5                  l3

        System.out.println(tree.get(5.5)); // ссылка
        System.out.println(tree.get(6.0)); // ссылка
        System.out.println(tree.get(5.1)); // null

        tree.remove(5.0);
        System.out.println(tree.getRoot().getValue());
        System.out.println(tree.getRoot().getRight().getValue());

        System.out.println();

        tree.preOrder();
    }
}
