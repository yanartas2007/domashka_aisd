package SEMESTROVKA_B_TREE;

public class BTree<K extends Comparable<K>, V> {
    private final int t;
    Node<K, V> root;

    public BTree(int t) {
        if (t < 2) throw new IllegalArgumentException(); // степень б дерева должна быть минимум 2
        this.t = t;
        this.root = new Node<K, V>(true);
    }

    public V search(K key) { // поиск элемента по ключу
        return search(root, key);
    }

    private V search(Node<K, V> node, K key) {
        int pos = node.findPosition(key);
        if (pos < node.keys.size() && key.compareTo(node.keys.get(pos)) == 0) {
            return node.values.get(pos);
        }
        if (node.leaf) {
            return null;
        }
        return search(node.children.get(pos), key);
    }

    public void insert(K key, V value) { // добавление элемента
        Node<K, V> r = root;
        if (r.keys.size() == 2 * t - 1) {
            Node<K, V> newRoot = new Node<>(false);
            newRoot.children.add(r);
            splitChild(newRoot, 0);
            root = newRoot;
            insertNonFull(root, key, value);
        } else {
            insertNonFull(r, key, value);
        }
    }

    private void splitChild(Node<K, V> parent, int index) { // деление детей при переполнении
        Node<K, V> child = parent.children.get(index);
        Node<K, V> newChild = new Node<K, V>(child.leaf);
        int mid = t - 1; // индекс среднего ключа

        // Сохраняем средний ключ и значение ДО удаления
        K midKey = child.keys.get(mid);
        V midVal = child.values.get(mid);

        // Перемещаем правую половину ключей в новый узел
        for (int i = mid + 1; i < child.keys.size(); i++) {
            newChild.keys.add(child.keys.get(i));
            newChild.values.add(child.values.get(i));
        }

        // Если узел не лист, переносим соответствующих потомков
        if (!child.leaf) {
            for (int i = mid + 1; i < child.children.size(); i++) {
                newChild.children.add(child.children.get(i));
            }
            // Обрезаем список потомков исходного узла
            while (child.children.size() > mid + 1) {
                child.children.remove(child.children.size() - 1);
            }
        }

        // Удаляем из child все ключи, начиная с mid
        while (child.keys.size() > mid) {
            child.keys.remove(child.keys.size() - 1);
            child.values.remove(child.values.size() - 1);
        }

        // Вставляем средний ключ в родителя
        parent.keys.add(index, midKey);
        parent.values.add(index, midVal);
        parent.children.add(index + 1, newChild);
    }

    private void insertNonFull(Node<K, V> node, K key, V value) { // добавление в точно неполную ноду
        int pos = node.findPosition(key);

        if (node.leaf) {
            node.keys.add(pos, key);
            node.values.add(pos, value);
        } else {
            Node<K, V> child = node.children.get(pos);
            if (child.keys.size() == 2 * t - 1) {
                splitChild(node, pos);
                if (key.compareTo(node.keys.get(pos)) > 0) {
                    pos++;
                }
                child = node.children.get(pos);
            }
            insertNonFull(child, key, value);
        }
    }

    public void print() { // вывод дерева
        print(root);
        System.out.println();
    }

    private void print(Node<K, V> node) { // рекурсивный вывод ноды
        if (node == null) return;
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.leaf) {
                print(node.children.get(i));
            }
            System.out.print(node.keys.get(i) + ":" + node.values.get(i) + " ");
        }
        if (!node.leaf) {
            print(node.children.get(node.children.size() - 1));
        }
    }




    public void delete(K key) {
        if (root == null) return;
        delete(root, key);
        // Если корень опустел и у него есть дети, новый корень — первый ребёнок
        if (root.keys.isEmpty() && !root.leaf) {
            root = root.children.get(0);
        }
    }

    private void delete(Node<K, V> node, K key) {
        int pos = node.findPosition(key);
        boolean keyInNode = pos < node.keys.size() && key.compareTo(node.keys.get(pos)) == 0;

        if (keyInNode) {
            if (node.leaf) {
                // Случай 1: ключ в листе
                node.keys.remove(pos);
                node.values.remove(pos);
            } else {
                // Случай 2: ключ во внутреннем узле
                deleteInternalNode(node, pos);
            }
        } else {
            if (node.leaf) {
                return; // ключ не найден
            }
            Node<K, V> child = node.children.get(pos);
            if (child.keys.size() == t - 1) {
                // У ребёнка минимальное количество ключей — нужно его пополнить
                fixChild(node, pos);
                // После фикса структура могла измениться, повторяем поиск в этом же узле
                delete(node, key);
            } else {
                delete(child, key);
            }
        }
    }

    private void deleteInternalNode(Node<K, V> node, int pos) {
        K key = node.keys.get(pos);
        Node<K, V> leftChild = node.children.get(pos);
        Node<K, V> rightChild = node.children.get(pos + 1);

        // Случай 2a: левый ребёнок имеет >= t ключей → берём предшественника
        if (leftChild.keys.size() >= t) {
            Node<K, V> predNode = getPredecessorNode(leftChild);
            K predKey = predNode.keys.get(predNode.keys.size() - 1);
            V predVal = predNode.values.get(predNode.values.size() - 1);
            node.keys.set(pos, predKey);
            node.values.set(pos, predVal);
            delete(leftChild, predKey);
        }
        // Случай 2b: правый ребёнок имеет >= t ключей → берём последователя
        else if (rightChild.keys.size() >= t) {
            Node<K, V> succNode = getSuccessorNode(rightChild);
            K succKey = succNode.keys.get(0);
            V succVal = succNode.values.get(0);
            node.keys.set(pos, succKey);
            node.values.set(pos, succVal);
            delete(rightChild, succKey);
        }
        // Случай 2c: оба ребёнка имеют t-1 ключей → сливаем
        else {
            mergeChildren(node, pos);
            // После слияния ключ удалён, удаляем его из объединённого узла
            Node<K, V> merged = node.children.get(pos); // левый ребёнок после слияния
            delete(merged, key);
        }
    }

    private Node<K, V> getPredecessorNode(Node<K, V> node) {
        if (node.leaf) return node;
        return getPredecessorNode(node.children.get(node.children.size() - 1));
    }

    private Node<K, V> getSuccessorNode(Node<K, V> node) {
        if (node.leaf) return node;
        return getSuccessorNode(node.children.get(0));
    }

    private void fixChild(Node<K, V> parent, int childIndex) {
        Node<K, V> child = parent.children.get(childIndex);

        // Пытаемся заимствовать у левого брата
        if (childIndex > 0) {
            Node<K, V> leftSibling = parent.children.get(childIndex - 1);
            if (leftSibling.keys.size() > t - 1) {
                borrowFromLeft(parent, childIndex, leftSibling, child);
                return;
            }
        }

        // Пытаемся заимствовать у правого брата
        if (childIndex < parent.children.size() - 1) {
            Node<K, V> rightSibling = parent.children.get(childIndex + 1);
            if (rightSibling.keys.size() > t - 1) {
                borrowFromRight(parent, childIndex, child, rightSibling);
                return;
            }
        }

        // Слияние с соседом
        if (childIndex > 0) {
            mergeChildren(parent, childIndex - 1);
        } else {
            mergeChildren(parent, childIndex);
        }
    }

    private void borrowFromLeft(Node<K, V> parent, int childIndex, Node<K, V> leftSibling, Node<K, V> child) { // воруем ключ у левого
        // Забираем ключ из родителя
        K parentKey = parent.keys.get(childIndex - 1);
        V parentValue = parent.values.get(childIndex - 1);

        // Перемещаем его в начало ребёнка
        child.keys.add(0, parentKey);
        child.values.add(0, parentValue);

        // Если ребёнок не лист, забираем последнего потомка левого брата
        if (!child.leaf) {
            Node<K, V> lastChildOfLeft = leftSibling.children.get(leftSibling.children.size() - 1);
            child.children.add(0, lastChildOfLeft);
            leftSibling.children.remove(leftSibling.children.size() - 1);
        }

        // Обновляем ключ в родителе берём последний ключ левого брата
        K newParentKey = leftSibling.keys.get(leftSibling.keys.size() - 1);
        V newParentValue = leftSibling.values.get(leftSibling.values.size() - 1);
        parent.keys.set(childIndex - 1, newParentKey);
        parent.values.set(childIndex - 1, newParentValue);

        // Удаляем последний ключ у левого брата
        leftSibling.keys.remove(leftSibling.keys.size() - 1);
        leftSibling.values.remove(leftSibling.values.size() - 1);
    }

    private void borrowFromRight(Node<K, V> parent, int childIndex, Node<K, V> child, Node<K, V> rightSibling) { // воруем справа
        // Забираем ключ из родителя
        K parentKey = parent.keys.get(childIndex);
        V parentValue = parent.values.get(childIndex);

        // Перемещаем его в конец ребёнка
        child.keys.add(parentKey);
        child.values.add(parentValue);

        // Если ребёнок не лист, забираем первого потомка правого брата
        if (!child.leaf) {
            Node<K, V> firstChildOfRight = rightSibling.children.get(0);
            child.children.add(firstChildOfRight);
            rightSibling.children.remove(0);
        }

        // Обновляем ключ в родителе берём первый ключ правого брата
        K newParentKey = rightSibling.keys.get(0);
        V newParentValue = rightSibling.values.get(0);
        parent.keys.set(childIndex, newParentKey);
        parent.values.set(childIndex, newParentValue);

        // Удаляем первый ключ у правого брата
        rightSibling.keys.remove(0);
        rightSibling.values.remove(0);
    }

    private void mergeChildren(Node<K, V> parent, int keyIndex) { // слияние 2 детей
        Node<K, V> leftChild = parent.children.get(keyIndex);
        Node<K, V> rightChild = parent.children.get(keyIndex + 1);

        // Добавляем ключ из родителя в левого ребёнка
        K parentKey = parent.keys.get(keyIndex);
        V parentValue = parent.values.get(keyIndex);
        leftChild.keys.add(parentKey);
        leftChild.values.add(parentValue);

        // Добавляем все ключи из правого ребёнка в левого
        leftChild.keys.addAll(rightChild.keys);
        leftChild.values.addAll(rightChild.values);

        // Добавляем потомков из правого ребёнка (если не листья)
        if (!leftChild.leaf) {
            leftChild.children.addAll(rightChild.children);
        }

        // Удаляем ключ и правого ребёнка из родителя
        parent.keys.remove(keyIndex);
        parent.values.remove(keyIndex);
        parent.children.remove(keyIndex + 1);
    }
}

// как же я заколебался писать комментарии и переводить названия функций