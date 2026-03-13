class NotificationQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public NotificationQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T notification) {
        Node<T> newNode = new Node<>(notification);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Priority boost: Adds to the front instead of the rear
    public void priorityEnqueue(T notification) {
        Node<T> newNode = new Node<>(notification);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.next = front;
            front = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null; // Queue is now empty
        }
        size--;
        return data;
    }

    public T front() {
        if (isEmpty()) return null;
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void displayPending() {
        Node<T> current = front;
        System.out.println("Pending Notifications:");
        while (current != null) {
            System.out.println("- " + current.data);
            current = current.next;
        }
    }
}