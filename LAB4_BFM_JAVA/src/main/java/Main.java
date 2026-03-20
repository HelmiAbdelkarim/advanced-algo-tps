import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        Post[] posts = {
                new Post(1, 10, "Post 1 preview", LocalDateTime.now(), 100, 25, 0),
                new Post(2, 11, "Post 2 preview", LocalDateTime.now(), 200, 60, 0),
                new Post(3, 12, "Post 3 preview", LocalDateTime.now(),  55, 20, 0),
                new Post(4, 13, "Post 4 preview", LocalDateTime.now(), 160, 60, 0),
        };

        System.out.println("=== Posts ===");
        for (Post p : posts) System.out.println("  " + p);

        // ---- Part A: max_engagement ----
        int max = PostAnalytics.maxEngagement(posts, 0, posts.length - 1);
        System.out.println("\n--- max_engagement ---");
        System.out.println("  Max score: " + max);           // Expected: 320

        // ---- Part B: sum and average ----
        int sum = PostAnalytics.sumEngagement(posts, 0, posts.length - 1);
        double avg = PostAnalytics.averageEngagement(posts, 0, posts.length - 1);
        System.out.println("\n--- sum_engagement ---");
        System.out.println("  Total: " + sum);               // Expected: 845
        System.out.println("  Average: " + avg);             // Expected: 211.25

        // ---- Part C: count_above_threshold ----
        int count = PostAnalytics.countAboveThreshold(posts, 0, posts.length - 1, 200);
        System.out.println("\n--- count_above_threshold(200) ---");
        System.out.println("  Count: " + count);             // Expected: 2

        // ---- Part D: merge_sort ----
        System.out.println("\n--- merge_sort_by_engagement (before) ---");
        for (Post p : posts) System.out.println("  " + p);
        PostAnalytics.mergeSortByEngagement(posts, 0, posts.length - 1);
        System.out.println("--- merge_sort_by_engagement (after, descending) ---");
        for (Post p : posts) System.out.println("  " + p);  // Expected: 320, 280, 150, 95

        // ---- Peak Hour Analysis ----
        int[] hourlyLikes = {5, 8, 12, 25, 30, 28, 15, 10, 7, 4, 3, 2,
                2, 3, 5,  7,  9, 11, 14, 12, 8, 6, 4, 2};
        int peakHour = PostAnalytics.findPeakHour(hourlyLikes, 0, hourlyLikes.length - 1);
        System.out.println("\n--- find_peak_hour ---");
        System.out.printf("  Peak at hour %d with %d likes%n",
                peakHour, hourlyLikes[peakHour]);  // Expected: hour 4, 30 likes

        // ---- Edge cases ----
        System.out.println("\n--- Edge cases ---");
        System.out.println("  maxEngagement(empty):  " + PostAnalytics.maxEngagement(posts, 3, 1));  // 0
        System.out.println("  sumEngagement(single): " + PostAnalytics.sumEngagement(posts, 0, 0));
        System.out.println("  countAbove(threshold=9999): "
                + PostAnalytics.countAboveThreshold(posts, 0, posts.length - 1, 9999));  // 0
    }
}