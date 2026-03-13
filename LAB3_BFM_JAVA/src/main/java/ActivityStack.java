class ActivityStack<T> {
    private Node<T> top;
    private Node<T> undoTop;
    private int size;

    public ActivityStack() {
        this.top = null;
        this.undoTop = null;
        this.size = 0;
    }

    public void push(T activity) {
        Node<T> newNode = new Node<>(activity);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) return null;
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) return null;
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void displayRecent(int n) {
        Node<T> current = top;
        int count = 0;
        System.out.println("Recent Activities:");
        while (current != null && count < n) {
            System.out.println("- " + current.data);
            current = current.next;
            count++;
        }
    }

    // Undo functionality: Pops from main stack and pushes to undo stack
    public void undoLast() {
        if (isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        T undoneActivity = this.pop();

        // Push to undo stack
        Node<T> undoNode = new Node<>(undoneActivity);
        undoNode.next = undoTop;
        undoTop = undoNode;

        System.out.println("Undid: " + undoneActivity);
    }
}