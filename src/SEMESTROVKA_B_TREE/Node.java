package SEMESTROVKA_B_TREE;

import java.util.ArrayList;
import java.util.List;

public class Node<K extends Comparable<K>, V> {
    List<K> keys = new ArrayList<>();
    List<V> values = new ArrayList<>();
    List<Node<K, V>> children = new ArrayList<>();
    boolean leaf;

    public Node(boolean leaf) {
        this.leaf = leaf;
    }

    public int findPosition(K key) { // поиск элемента по ключу
        int pos = 0;
        while (pos < keys.size() && key.compareTo(keys.get(pos)) > 0) {
            pos++;
        }
        return pos;
    }

    @Override
    public String toString() {
        return keys.toString();
    }
}