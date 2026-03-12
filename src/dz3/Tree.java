package dz3;

public class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;
    private int height;

    public Tree() {
        this.root = null;
        this.height = 0;
    }

    public Tree(TreeNode<T> root) {
        this.root = root;
        this.height = updateHeight(root);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public int getHeight() {
        return height;
    }

    public void add(T value) {
        if (root == null) {
            root = new TreeNode<>(value);
        } else {
            add(root, value);
        }
        height = updateHeight(root);
    }

    private void add(TreeNode<T> current, T value) {
        if (current.getValue().compareTo(value) > 0) {
            if (current.getLeft() == null) {
                current.setLeft(new TreeNode<>(value, current));
            } else {
                add(current.getLeft(), value);
            }
        } else {
            if (current.getRight() == null) {
                current.setRight(new TreeNode<>(value, current));
            } else {
                add(current.getRight(), value);
            }
        }
    }

    public TreeNode<T> get(T value) {
        if (root == null) return null;
        return get(root, value);
    }

    private TreeNode<T> get(TreeNode<T> current, T value) {
        if (current == null) return null;
        int comparing = current.getValue().compareTo(value);
        if (comparing == 0) return current;
        if (comparing > 0) return get(current.getLeft(), value);
        else return get(current.getRight(), value);
    }

    public void remove(T value) {
        TreeNode<T> trash = get(value);
        if (trash == null) return;
        remove(trash);
        height = updateHeight(root);
    }

    private void remove(TreeNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            if (node.isRoot()) {
                root = null;
            } else {
                TreeNode<T> parent = node.getParent();
                if (parent.getLeft() == node) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                node.setParent(null);
            }
            return;
        }

        if (node.getLeft() == null || node.getRight() == null) {
            TreeNode<T> son = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            if (node.isRoot()) {
                root = son;
                son.setParent(null);
            } else {
                TreeNode<T> parent = node.getParent();
                if (parent.getLeft() == node) {
                    parent.setLeft(son);
                } else {
                    parent.setRight(son);
                }
                son.setParent(parent);
            }
            node.setParent(null);
            return;
        }

        TreeNode<T> max = node.getRight();
        while (max.getLeft() != null) {
            max = max.getLeft();
        }
        T max_value = max.getValue();
        remove(max);
        node.setValue(max_value);
    }
    private int updateHeight(TreeNode<T> node) {
        if (node == null) return 0;
        int a = updateHeight(node.getLeft());
        int b = updateHeight(node.getRight());
        return 1 + (a > b ? a : b);
    }
    public void preOrder() {
        preOrder(getRoot());
    }

    public void postOrder() {
        postOrder(getRoot());
    }

    public void inOrder() {
        inOrder(getRoot());
    }

    public void preOrder(TreeNode<T> r) {
        if (r == null){
            return;
        }
        System.out.println(r.getValue());
            preOrder(r.getLeft());
            preOrder(r.getRight());
    }

    public void inOrder(TreeNode<T> r) {
        if (r == null){
            return;
        }
            inOrder(r.getLeft());
        System.out.println(r.getValue());

            inOrder(r.getRight());
    }

    public void postOrder(TreeNode<T> r) {
        if (r == null){
            return;
        }
            postOrder(r.getLeft());
            postOrder(r.getRight());
        System.out.println(r.getValue());
    }
}