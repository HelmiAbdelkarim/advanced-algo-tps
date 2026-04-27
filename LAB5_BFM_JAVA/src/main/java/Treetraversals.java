import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeTraversals {

    // =========================================================
    // PART A — IN-ORDER TRAVERSAL (Left -> Root -> Right)
    // =========================================================

    // Returns list of Category values in in-order sequence
    public static List<Category> in_order_collect(CategoryNode node) {
        List<Category> res = new ArrayList<>();
        if (node == null) return res;

        res.addAll(in_order_collect(node.getLeft()));
        res.add(node.getValue());
        res.addAll(in_order_collect(node.getRight()));

        return res;
    }

    // Returns the running total of post_count across all nodes (in-order)
    public static int in_order_accumulate_posts(CategoryNode node) {
        if (node == null) return 0;

        int total = in_order_accumulate_posts(node.getLeft());
        total += node.getValue().post_count;
        total += in_order_accumulate_posts(node.getRight());

        return total;
    }

    // Returns the k-th category in in-order sequence (1-indexed)
    public static Category in_order_find_kth(int k, CategoryNode node) {
        List<Category> res = in_order_collect(node);
        if (k < 1 || k > res.size()) return null;
        return res.get(k - 1);  // k is 1-indexed, list is 0-indexed
    }

    // =========================================================
    // PART B — PRE-ORDER TRAVERSAL (Root -> Left -> Right)
    // =========================================================

    // Returns indented string representation of tree structure
    public static List<String> pre_order_export(CategoryNode node, int depth) {
        List<String> res = new ArrayList<>();
        if (node == null) return res;

        String indent = "  ".repeat(depth);
        res.add(indent + node.getValue().name + "(" + node.getValue().post_count + ")");

        res.addAll(pre_order_export(node.getLeft(),  depth + 1));
        res.addAll(pre_order_export(node.getRight(), depth + 1));

        return res;
    }

    // Creates a deep copy of the entire tree
    public static CategoryNode pre_order_copy(CategoryNode node) {
        if (node == null) return null;

        // Create a NEW node with the same value — not a reference copy
        CategoryNode root = new CategoryNode(node.getValue());
        root.setLeft(pre_order_copy(node.getLeft()));
        root.setRight(pre_order_copy(node.getRight()));

        return root;
    }

    // Converts tree to pipe-separated string: "Technology(150)|Programming(85)|..."
    public static String pre_order_serialize(CategoryNode node) {
        if (node == null) return "";

        String res = node.getValue().name + "(" + node.getValue().post_count + ")";

        String leftPart  = pre_order_serialize(node.getLeft());
        String rightPart = pre_order_serialize(node.getRight());

        if (!leftPart.isEmpty())  res = res + "|" + leftPart;
        if (!rightPart.isEmpty()) res = res + "|" + rightPart;

        return res;
    }

    // =========================================================
    // PART C — POST-ORDER TRAVERSAL (Left -> Right -> Root)
    // =========================================================

    // Collects all Category values in post-order sequence
    public static List<Category> post_order_collect(CategoryNode node) {
        List<Category> res = new ArrayList<>();
        if (node == null) return res;

        res.addAll(post_order_collect(node.getLeft()));
        res.addAll(post_order_collect(node.getRight()));
        res.add(node.getValue());

        return res;
    }

    // Returns total post_count of a node including all its descendants
    public static int post_order_total_posts(CategoryNode node) {
        if (node == null) return 0;

        int total = post_order_total_posts(node.getLeft());
        total += post_order_total_posts(node.getRight());
        total += node.getValue().post_count;

        return total;
    }

    // Collects all leaf nodes (nodes with no children) in post-order
    public static List<Category> post_order_collect_leaves(CategoryNode node) {
        List<Category> res = new ArrayList<>();
        if (node == null) return res;

        res.addAll(post_order_collect_leaves(node.getLeft()));
        res.addAll(post_order_collect_leaves(node.getRight()));

        if (node.getLeft() == null && node.getRight() == null) {
            res.add(node.getValue());
        }

        return res;
    }

    // Helper: collects the depth of every leaf node
    private static List<Integer> collect_leaf_depths(CategoryNode node, int depth) {
        List<Integer> res = new ArrayList<>();
        if (node == null) return res;

        // Base case: leaf node — record its depth
        if (node.getLeft() == null && node.getRight() == null) {
            res.add(depth);
            return res;
        }

        res.addAll(collect_leaf_depths(node.getLeft(),  depth + 1));
        res.addAll(collect_leaf_depths(node.getRight(), depth + 1));

        return res;
    }

    // Returns average depth of all leaf nodes
    public static double post_order_average_depth(CategoryNode node) {
        List<Integer> depths = collect_leaf_depths(node, 0);
        if (depths.isEmpty()) return 0;

        int total = 0;
        for (int d : depths) {
            total += d;
        }
        return (double) total / depths.size();
    }

    // =========================================================
    // ANALYTICS
    // =========================================================

    // Returns the node with the highest post_count (not including children)
    public static CategoryNode find_most_popular_category(CategoryNode node) {
        if (node == null) return null;

        CategoryNode leftBest  = find_most_popular_category(node.getLeft());
        CategoryNode rightBest = find_most_popular_category(node.getRight());
        CategoryNode best = node;

        if (leftBest  != null && leftBest.getValue().post_count  > best.getValue().post_count)
            best = leftBest;
        if (rightBest != null && rightBest.getValue().post_count > best.getValue().post_count)
            best = rightBest;

        return best;
    }

    // Helper: counts direct children of a node (0, 1, or 2 in binary tree)
    private static int count_direct_children(CategoryNode node) {
        int count = 0;
        if (node.getLeft()  != null) count++;
        if (node.getRight() != null) count++;
        return count;
    }

    // Returns the node with the most direct children
    public static CategoryNode category_with_most_subcategories(CategoryNode node) {
        if (node == null) return null;

        CategoryNode leftBest  = category_with_most_subcategories(node.getLeft());
        CategoryNode rightBest = category_with_most_subcategories(node.getRight());
        CategoryNode best = node;

        if (leftBest  != null && count_direct_children(leftBest)  > count_direct_children(best))
            best = leftBest;
        if (rightBest != null && count_direct_children(rightBest) > count_direct_children(best))
            best = rightBest;

        return best;
    }

    // =========================================================
    // ITERATIVE TRAVERSALS (Q3 of complexity analysis)
    // =========================================================

    public static List<Category> iterative_in_order(CategoryNode root) {
        List<Category> res = new ArrayList<>();
        Stack<CategoryNode> stack = new Stack<>();
        CategoryNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }
            current = stack.pop();
            res.add(current.getValue());
            current = current.getRight();
        }
        return res;
    }

    public static List<Category> iterative_pre_order(CategoryNode root) {
        List<Category> res = new ArrayList<>();
        if (root == null) return res;

        Stack<CategoryNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            CategoryNode node = stack.pop();
            res.add(node.getValue());
            // Push right first so left is popped first
            if (node.getRight() != null) stack.push(node.getRight());
            if (node.getLeft()  != null) stack.push(node.getLeft());
        }
        return res;
    }

    public static List<Category> iterative_post_order(CategoryNode root) {
        List<Category> res = new ArrayList<>();
        if (root == null) return res;

        Stack<CategoryNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            CategoryNode node = stack.pop();
            res.add(node.getValue());
            // Push left first so right is popped first (reverse pre-order)
            if (node.getLeft()  != null) stack.push(node.getLeft());
            if (node.getRight() != null) stack.push(node.getRight());
        }
        // Reverse gives Left -> Right -> Root
        java.util.Collections.reverse(res);
        return res;
    }
}