package main.java;

public class ActivitySegmentTree {
    private int n;
    private int[] treeSum;
    private int[] treeMax; // Added to support get_range_max()
    private int nodeCount = 0;

    public ActivitySegmentTree(int[] arr) {
        this.n = arr.length;
        if (n > 0) {
            treeSum = new int[4 * n];
            treeMax = new int[4 * n];
            build(arr, 0, 0, n - 1);
        }
    }

    private void build(int[] arr, int node, int start, int end) {
        nodeCount++;
        if (start == end) {
            treeSum[node] = arr[start];
            treeMax[node] = arr[start];
            return;
        }
        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        build(arr, leftChild, start, mid);
        build(arr, rightChild, mid + 1, end);

        treeSum[node] = treeSum[leftChild] + treeSum[rightChild];
        treeMax[node] = Math.max(treeMax[leftChild], treeMax[rightChild]);
    }

    public void updateDay(int targetDay, int addedPosts) {
        updateDayHelper(0, 0, n - 1, targetDay, addedPosts);
    }

    private void updateDayHelper(int node, int start, int end, int targetDay, int addedPosts) {
        if (start == end) {
            treeSum[node] += addedPosts;
            treeMax[node] += addedPosts;
            return;
        }
        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        if (targetDay <= mid) {
            updateDayHelper(leftChild, start, mid, targetDay, addedPosts);
        } else {
            updateDayHelper(rightChild, mid + 1, end, targetDay, addedPosts);
        }

        treeSum[node] = treeSum[leftChild] + treeSum[rightChild];
        treeMax[node] = Math.max(treeMax[leftChild], treeMax[rightChild]);
    }

    public int query(int L, int R) {
        return querySumHelper(0, 0, n - 1, L, R);
    }

    private int querySumHelper(int node, int start, int end, int L, int R) {
        if (L <= start && end <= R) return treeSum[node];
        if (end < L || start > R) return 0;

        int mid = (start + end) / 2;
        int leftSum = querySumHelper(2 * node + 1, start, mid, L, R);
        int rightSum = querySumHelper(2 * node + 2, mid + 1, end, L, R);
        return leftSum + rightSum;
    }

    public int getRangeMax(int L, int R) {
        return queryMaxHelper(0, 0, n - 1, L, R);
    }

    private int queryMaxHelper(int node, int start, int end, int L, int R) {
        if (L <= start && end <= R) return treeMax[node];
        if (end < L || start > R) return Integer.MIN_VALUE; // Return smallest possible for max comparison

        int mid = (start + end) / 2;
        int leftMax = queryMaxHelper(2 * node + 1, start, mid, L, R);
        int rightMax = queryMaxHelper(2 * node + 2, mid + 1, end, L, R);
        return Math.max(leftMax, rightMax);
    }

    // Mathematical height of a segment tree: ceil(log2(n)) + 1
    public int getHeight() {
        return (int) (Math.ceil(Math.log(n) / Math.log(2)) + 1);
    }

    public int getTreeSize() {
        return nodeCount;
    }
}
