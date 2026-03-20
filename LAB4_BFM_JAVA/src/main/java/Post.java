import java.time.LocalDateTime;

public class Post {

    // Attributes
    private int postId;
    private int userId;
    private String contentPreview;
    private LocalDateTime timestamp;
    private int likes;
    private int comments;
    private int shares;
    private int engagementScore;

    // Constructor — computes engagementScore automatically
    public Post(int postId, int userId, String contentPreview,
                LocalDateTime timestamp, int likes, int comments, int shares) {
        this.postId         = postId;
        this.userId         = userId;
        this.contentPreview = contentPreview;
        this.timestamp      = timestamp;
        this.likes          = likes;
        this.comments       = comments;
        this.shares         = shares;
        this.engagementScore = computeEngagement(likes, comments, shares);
    }

    // Observer
    public int getEngagementScore() {
        return engagementScore;
    }

    public int getPostId()   { return postId; }
    public int getUserId()   { return userId; }
    public String getContentPreview() { return contentPreview; }

    // Formula: likes×1 + comments×2 + shares×3
    private static int computeEngagement(int likes, int comments, int shares) {
        return likes * 1 + comments * 2 + shares * 3;
    }

    @Override
    public String toString() {
        return String.format("Post[id=%d, user=%d, score=%d, preview=\"%s\"]",
                postId, userId, engagementScore, contentPreview);
    }
}