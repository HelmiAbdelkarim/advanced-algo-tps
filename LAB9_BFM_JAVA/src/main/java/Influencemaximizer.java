import java.util.*;

public class InfluenceMaximizer {

    public static class Result {
        public final int totalInfluence;
        public final List<Integer> selectedUsers;

        public Result(int totalInfluence, List<Integer> selectedUsers) {
            this.totalInfluence  = totalInfluence;
            this.selectedUsers   = Collections.unmodifiableList(new ArrayList<>(selectedUsers));
        }

        @Override
        public String toString() {
            return "Result{totalInfluence=" + totalInfluence +
                    ", selectedUsers=" + selectedUsers + "}";
        }
    }

    public static boolean isWithinBudget(List<Integer> selection, int[] costs, int budget) {
        int totalCost = 0;
        for (int userIndex : selection) {
            totalCost += costs[userIndex];
        }
        return totalCost <= budget;
    }

    public static Result fastAlternativeStrategy(int budget, int[] costs, int[] influences) {
        int n = costs.length;

        // Build (ratio, original-index) pairs
        List<double[]> ratios = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double ratio = (double) influences[i] / costs[i];
            ratios.add(new double[]{ratio, i});
        }

        // Sort descending by ratio
        ratios.sort((a, b) -> Double.compare(b[0], a[0]));

        int totalInfluence   = 0;
        int currentBudget    = budget;
        List<Integer> selected = new ArrayList<>();

        for (double[] entry : ratios) {
            int i = (int) entry[1];
            if (costs[i] <= currentBudget) {
                selected.add(i);
                totalInfluence += influences[i];
                currentBudget  -= costs[i];
            }
        }

        return new Result(totalInfluence, selected);
    }

    public static Result maximizeReach(int budget, int[] costs, int[] influences) {
        int n = costs.length;

        // dp[i][w] = max influence using first i users with budget w
        int[][] dp = new int[n + 1][budget + 1];

        // Build table bottom-up
        for (int i = 1; i <= n; i++) {
            int currentCost      = costs[i - 1];
            int currentInfluence = influences[i - 1];

            for (int w = 1; w <= budget; w++) {
                if (currentCost <= w) {
                    int takeIt  = currentInfluence + dp[i - 1][w - currentCost];
                    int leaveIt = dp[i - 1][w];
                    dp[i][w] = Math.max(takeIt, leaveIt);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int maxInfluence = dp[n][budget];

        // Backtrack to find selected users
        List<Integer> selectedUsers = new ArrayList<>();
        int w = budget;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedUsers.add(i - 1);       // 0-based user index
                w -= costs[i - 1];
            }
        }

        return new Result(maxInfluence, selectedUsers);
    }
}