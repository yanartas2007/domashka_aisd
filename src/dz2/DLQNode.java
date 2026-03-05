package dz2;

class DLQNode<T> {
    public T data;
    public DLQNode<T> next;
    public DLQNode<T> prev;

    public DLQNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
