public class PostAnalytics {

    // -------------------------------------------------------------------------
    // Part A — Maximum Engagement
    // -------------------------------------------------------------------------

    /**
     * Recursively finds the highest engagement score in posts[left..right].
     * Returns 0 for an empty or invalid range.
     */
    public static int maxEngagement(Post[] posts, int left, int right) {
        // Edge case: invalid bounds
        if (left > right) return 0;

        // Base case: single post
        if (left == right) return posts[left].getEngagementScore();

        // Divide
        int mid = (left + right) / 2;

        // Conquer
        int maxL = maxEngagement(posts, left, mid);
        int maxR = maxEngagement(posts, mid + 1, right);

        // Combine
        return (maxL > maxR) ? maxL : maxR;
    }

    // -------------------------------------------------------------------------
    // Part B — Sum and Average
    // -------------------------------------------------------------------------

    /**
     * Recursively sums all engagement scores in posts[left..right].
     */
    public static int sumEngagement(Post[] posts, int left, int right) {
        // Edge case
        if (left > right) return 0;

        // Base case
        if (left == right) return posts[left].getEngagementScore();

        // Divide
        int mid = (left + right) / 2;

        // Conquer + Combine
        int sumL = sumEngagement(posts, left, mid);
        int sumR = sumEngagement(posts, mid + 1, right);
        return sumL + sumR;
    }

    /**
     * Computes the average engagement score over posts[left..right].
     * Uses sumEngagement internally — not itself recursive.
     */
    public static double averageEngagement(Post[] posts, int left, int right) {
        // Prevent division by zero
        if (left > right) return 0.0;

        int totalSum = sumEngagement(posts, left, right);
        int count    = (right - left) + 1;
        return (double) totalSum / count;
    }

    // -------------------------------------------------------------------------
    // Part C — Count Above Threshold
    // -------------------------------------------------------------------------

    /**
     * Recursively counts posts whose engagement score exceeds the threshold.
     */
    public static int countAboveThreshold(Post[] posts, int left, int right, int threshold) {
        // Edge case
        if (left > right) return 0;

        // Base case
        if (left == right) {
            return (posts[left].getEngagementScore() > threshold) ? 1 : 0;
        }

        // Divide
        int mid = (left + right) / 2;

        // Conquer
        int countL = countAboveThreshold(posts, left, mid, threshold);
        int countR = countAboveThreshold(posts, mid + 1, right, threshold);

        // Combine
        return countL + countR;
    }

    // -------------------------------------------------------------------------
    // Part D — Merge Sort by Engagement (descending)
    // -------------------------------------------------------------------------

    /**
     * Recursively sorts posts[left..right] by engagement score (highest first).
     * Modifies the array in-place.
     */
    public static void mergeSortByEngagement(Post[] posts, int left, int right) {
        // Base case: 0 or 1 element is already sorted
        if (left >= right) return;

        // Divide
        int mid = (left + right) / 2;

        // Conquer
        mergeSortByEngagement(posts, left, mid);
        mergeSortByEngagement(posts, mid + 1, right);

        // Combine
        merge(posts, left, mid, right);
    }

    /**
     * Merges two sorted sub-arrays posts[left..mid] and posts[mid+1..right]
     * in descending order of engagement score.
     */
    public static void merge(Post[] posts, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = 0;

        Post[] temp = new Post[right - left + 1];

        // Compare and merge the two halves
        while (i <= mid && j <= right) {
            if (posts[i].getEngagementScore() >= posts[j].getEngagementScore()) {
                temp[k] = posts[i];
                i++;
            } else {
                temp[k] = posts[j];
                j++;
            }
            k++;
        }

        // Exhaust remaining left-half elements
        while (i <= mid) {
            temp[k] = posts[i];
            i++;
            k++;
        }

        // Exhaust remaining right-half elements
        while (j <= right) {
            temp[k] = posts[j];
            j++;
            k++;
        }

        // Copy sorted temp back into the original array
        for (int index = 0; index < temp.length; index++) {
            posts[left + index] = temp[index];
        }
    }

    // -------------------------------------------------------------------------
    // Peak Hour Analysis
    // -------------------------------------------------------------------------

    /**
     * Recursively finds the index of the peak hour in likes[left..right].
     * Assumes the array has at most one peak (unimodal or locally peaked).
     * Returns the index of the hour with maximum likes.
     */
    public static int findPeakHour(int[] likes, int left, int right) {
        // Base case: single hour
        if (left == right) return left;

        // Divide
        int mid = (left + right) / 2;

        // Check whether mid qualifies as a local peak
        boolean isLeftValid  = (mid == left)  || (likes[mid] >= likes[mid - 1]);
        boolean isRightValid = (mid == right) || (likes[mid] >= likes[mid + 1]);

        if (isLeftValid && isRightValid) return mid;

        // Conquer: go left if the upward slope points left
        if (mid > left && likes[mid - 1] > likes[mid]) {
            return findPeakHour(likes, left, mid - 1);
        } else {
            return findPeakHour(likes, mid + 1, right);
        }
    }
}