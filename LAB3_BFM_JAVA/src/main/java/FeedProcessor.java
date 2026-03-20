class FeedProcessor<T> {
    private ActivityStack<T> recentActivities;
    private NotificationQueue<T> notificationQueue;
    private NotificationQueue<T> processedLog;

    public FeedProcessor() {
        this.recentActivities = new ActivityStack<>();
        this.notificationQueue = new NotificationQueue<>();
        this.processedLog = new NotificationQueue<>();
    }

    // Expose the notification queue so we can add incoming items
    public NotificationQueue<T> getNotificationQueue() {
        return notificationQueue;
    }

    public void processIncoming() {
        if (!notificationQueue.isEmpty()) {
            T item = notificationQueue.dequeue();
            recentActivities.push(item);
        }
    }

    public void batchProcess(int k) {
        int count = 0;
        while (!notificationQueue.isEmpty() && count < k) {
            processIncoming();
            count++;
        }
    }

    public void clearHistory() {
        while (!recentActivities.isEmpty()) {
            T item = recentActivities.pop();
            processedLog.enqueue(item);
        }
    }

    public void getStats() {
        System.out.println("=== Feed Processor Stats ===");
        System.out.println("Recent Stack Size: " + recentActivities.size());
        System.out.println("Notification Queue Size: " + notificationQueue.size());
        System.out.println("Processed Log Size: " + processedLog.size());
    }
}