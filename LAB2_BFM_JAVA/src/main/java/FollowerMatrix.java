import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FollowerMatrix {

    private boolean[][] matrix;
    private int size;
    private int userCount;

    public FollowerMatrix(int userCount) {
        this.userCount = userCount;
        this.size = userCount + 1; // +1 so user IDs map directly to indices (1-based)
        this.matrix = new boolean[size][size];
    }

    public void follow(int follower, int followee) {
        matrix[follower][followee] = true;
    }

    public void unfollow(int follower, int followee) {
        matrix[follower][followee] = false;
    }

    public boolean isFollowing(int follower, int followee) {
        return matrix[follower][followee];
    }

    public List<Integer> getFollowers(int user) {
        List<Integer> followers = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            if (matrix[i][user]) {
                followers.add(i);
            }
        }
        return followers;
    }

    public List<Integer> getFollowing(int user) {
        List<Integer> following = new ArrayList<>();
        for (int j = 1; j <= userCount; j++) {
            if (matrix[user][j]) {
                following.add(j);
            }
        }
        return following;
    }

    public List<int[]> getMutualFollows() {
        List<int[]> mutuals = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            // Start from i + 1 to avoid duplicate pairs and self-follows
            for (int j = i + 1; j <= userCount; j++) {
                if (matrix[i][j] && matrix[j][i]) {
                    mutuals.add(new int[]{i, j});
                }
            }
        }
        return mutuals;
    }

    public float calculateInfluence(int user) {
        int followersCount = getFollowers(user).size();
        int followingCount = getFollowing(user).size();
        return (float) (followersCount + followingCount) / userCount;
    }
}