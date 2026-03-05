package dz2;

public class DLQueye<T> { // не хочу делать и стек и очередь, поэтому вот двусвязная очередь
    private DLQNode<T> head;
    private DLQNode<T> tail;
    private int size;

    public DLQueye() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(T item) {
        addLast(item);
    }

    public T dequeue() {
        return removeFirst();
    }

    public T peek() {
        return getFirst();
    }

    public void add(T item) {
        addLast(item);
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new AssertionError("Очередь пуста");
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return data;
    }
    public T removeLast() {
        if (isEmpty()) {
            throw new AssertionError("Очередь пуста");
        }
        T data = tail.data;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return data;
    }


    public T getFirst() {
        if (isEmpty()) {
            throw new AssertionError("Очередь пуста");
        }
        return head.data;
    }


    public T getLast() {
        if (isEmpty()) {
            throw new AssertionError("Очередь пуста");
        }
        return tail.data;
    }



    public T pop() {
        return removeFirst();
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return size;
    }


    public void addLast(T item) {
        DLQNode<T> newDLQNode = new DLQNode<>(item);
        if (isEmpty()) {
            head = tail = newDLQNode;
        } else {
            tail.next = newDLQNode;
            newDLQNode.prev = tail;
            tail = newDLQNode;
        }
        size++;
    }

    public void addFirst(T item) {
        DLQNode<T> newDLQNode = new DLQNode<>(item);
        if (isEmpty()) {
            head = tail = newDLQNode;
        } else {
            head.prev = newDLQNode;
            newDLQNode.next = head;
            head = newDLQNode;
        }
        size++;
    }
}
