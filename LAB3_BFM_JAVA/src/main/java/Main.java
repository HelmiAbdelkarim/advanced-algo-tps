public class Main {
    public static void main(String[] args) {
        System.out.println("=== PART 1: LAB MANUAL EXAMPLE ===");
        testLabExample();
    }

    private static void testLabExample() {
        FeedProcessor<String> processor = new FeedProcessor<>();

        processor.getNotificationQueue().enqueue("New follower");
        processor.getNotificationQueue().enqueue("New like");
        processor.getNotificationQueue().enqueue("New comment");

        processor.getRecentActivities().push("Liked video");
        processor.getRecentActivities().push("Commented on post");
        processor.getRecentActivities().push("Shared photo");

        System.out.println("--- Before processing ---");
        processor.getRecentActivities().displayRecent(5);
        processor.getNotificationQueue().displayPending();

        System.out.println("\nExecuting processIncoming()...");
        processor.processIncoming();

        System.out.println("\n--- After processing ---");
        processor.getRecentActivities().displayRecent(5);
        processor.getNotificationQueue().displayPending();
    }
}