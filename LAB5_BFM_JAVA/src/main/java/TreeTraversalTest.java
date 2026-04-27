import java.util.List;

public class TreeTraversalTest {

    // =========================================================
    // TREE BUILDER — builds the example tree from the lab
    // =========================================================
    //
    //           Technology(150)
    //          /               \
    //    Programming(85)      Design(65)
    //       /        \         /       \
    //   Python(42)  Java(30) UI/UX(38) Graphics(22)
    //    /     \
    // Django(18) Flask(12)
    //
    static CategoryNode buildLabTree() {
        CategoryNode technology   = new CategoryNode(new Category("1", "Technology",   150));
        CategoryNode programming  = new CategoryNode(new Category("2", "Programming",   85));
        CategoryNode design       = new CategoryNode(new Category("3", "Design",         65));
        CategoryNode python       = new CategoryNode(new Category("4", "Python",         42));
        CategoryNode java         = new CategoryNode(new Category("5", "Java",           30));
        CategoryNode uiux         = new CategoryNode(new Category("6", "UI/UX",          38));
        CategoryNode graphics     = new CategoryNode(new Category("7", "Graphics",       22));
        CategoryNode django       = new CategoryNode(new Category("8", "Django",         18));
        CategoryNode flask        = new CategoryNode(new Category("9", "Flask",          12));

        technology.setLeft(programming);
        technology.setRight(design);
        programming.setLeft(python);
        programming.setRight(java);
        design.setLeft(uiux);
        design.setRight(graphics);
        python.setLeft(django);
        python.setRight(flask);

        return technology;
    }

    // Single node tree (edge case)
    static CategoryNode buildSingleNode() {
        return new CategoryNode(new Category("1", "Solo", 100));
    }

    // Degenerate tree — like a linked list going left (edge case)
    static CategoryNode buildDegenerateTree() {
        CategoryNode a = new CategoryNode(new Category("1", "A", 10));
        CategoryNode b = new CategoryNode(new Category("2", "B", 20));
        CategoryNode c = new CategoryNode(new Category("3", "C", 30));
        a.setLeft(b);
        b.setLeft(c);
        return a;
    }

    // =========================================================
    // HELPERS
    // =========================================================

    static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  " + title);
        System.out.println("=".repeat(50));
    }

    static void check(String testName, Object expected, Object actual) {
        boolean passed = expected.toString().equals(actual.toString());
        System.out.println((passed ? "[PASS] " : "[FAIL] ") + testName);
        if (!passed) {
            System.out.println("       Expected : " + expected);
            System.out.println("       Got      : " + actual);
        }
    }

    // =========================================================
    // MAIN — all test cases
    // =========================================================

    public static void main(String[] args) {
        CategoryNode root   = buildLabTree();
        CategoryNode single = buildSingleNode();
        CategoryNode degen  = buildDegenerateTree();

        // -------------------------------------------------
        printHeader("PART A — IN-ORDER COLLECT");
        // -------------------------------------------------

        List<Category> inOrder = TreeTraversals.in_order_collect(root);
        check("In-order first node is Django",
                "Django(18)", inOrder.get(0).toString());
        check("In-order last node is Graphics",
                "Graphics(22)", inOrder.get(inOrder.size() - 1).toString());
        check("In-order size = 9 nodes",
                "9", String.valueOf(inOrder.size()));
        check("In-order middle node is Technology",
                "Technology(150)", inOrder.get(5).toString());

        // Edge cases
        check("In-order on null returns empty list",
                "0", String.valueOf(TreeTraversals.in_order_collect(null).size()));
        check("In-order on single node returns that node",
                "Solo(100)", TreeTraversals.in_order_collect(single).get(0).toString());

        // -------------------------------------------------
        printHeader("PART A — IN-ORDER ACCUMULATE POSTS");
        // -------------------------------------------------

        check("Total posts = 462",
                "462", String.valueOf(TreeTraversals.in_order_accumulate_posts(root)));
        check("Null tree = 0 posts",
                "0", String.valueOf(TreeTraversals.in_order_accumulate_posts(null)));
        check("Single node = 100 posts",
                "100", String.valueOf(TreeTraversals.in_order_accumulate_posts(single)));

        // -------------------------------------------------
        printHeader("PART A — IN-ORDER FIND KTH");
        // -------------------------------------------------

        // In-order: Django, Python, Flask, Programming, Java, Technology, UI/UX, Design, Graphics
        check("1st in-order = Django",
                "Django(18)", TreeTraversals.in_order_find_kth(1, root).toString());
        check("6th in-order = Technology",
                "Technology(150)", TreeTraversals.in_order_find_kth(6, root).toString());
        check("9th in-order = Graphics",
                "Graphics(22)", TreeTraversals.in_order_find_kth(9, root).toString());
        check("k=0 returns null (invalid)",
                "null", String.valueOf(TreeTraversals.in_order_find_kth(0, root)));
        check("k=10 returns null (out of range)",
                "null", String.valueOf(TreeTraversals.in_order_find_kth(10, root)));

        // -------------------------------------------------
        printHeader("PART B — PRE-ORDER EXPORT");
        // -------------------------------------------------

        List<String> exported = TreeTraversals.pre_order_export(root, 0);
        check("Export first line = Technology (no indent)",
                "Technology(150)", exported.get(0));
        check("Export second line = Programming (2 spaces indent)",
                "  Programming(85)", exported.get(1));
        check("Export third line = Python (4 spaces indent)",
                "    Python(42)", exported.get(2));
        check("Export has 9 lines",
                "9", String.valueOf(exported.size()));

        // -------------------------------------------------
        printHeader("PART B — PRE-ORDER COPY");
        // -------------------------------------------------

        CategoryNode copy = TreeTraversals.pre_order_copy(root);
        check("Copy root value matches",
                root.getValue().toString(), copy.getValue().toString());
        check("Copy is a different object (deep copy)",
                "true", String.valueOf(copy != root));
        check("Copy left child value matches",
                root.getLeft().getValue().toString(), copy.getLeft().getValue().toString());
        check("Copy left child is different object",
                "true", String.valueOf(copy.getLeft() != root.getLeft()));
        check("Copy of null returns null",
                "null", String.valueOf(TreeTraversals.pre_order_copy(null)));

        // -------------------------------------------------
        printHeader("PART B — PRE-ORDER SERIALIZE");
        // -------------------------------------------------

        String serialized = TreeTraversals.pre_order_serialize(root);
        check("Serialized starts with Technology",
                "true", String.valueOf(serialized.startsWith("Technology(150)")));
        check("Serialized contains Django",
                "true", String.valueOf(serialized.contains("Django(18)")));
        check("Serialized contains pipe separators",
                "true", String.valueOf(serialized.contains("|")));
        check("Serialize null = empty string",
                "", TreeTraversals.pre_order_serialize(null));

        // -------------------------------------------------
        printHeader("PART C — POST-ORDER COLLECT");
        // -------------------------------------------------

        List<Category> postOrder = TreeTraversals.post_order_collect(root);
        check("Post-order first = Django (deepest left leaf)",
                "Django(18)", postOrder.get(0).toString());
        check("Post-order last = Technology (root is last)",
                "Technology(150)", postOrder.get(postOrder.size() - 1).toString());
        check("Post-order size = 9",
                "9", String.valueOf(postOrder.size()));

        // -------------------------------------------------
        printHeader("PART C — POST-ORDER TOTAL POSTS");
        // -------------------------------------------------

        // Python subtree: Django(18) + Flask(12) + Python(42) = 72
        check("Python subtree total = 72",
                "72", String.valueOf(TreeTraversals.post_order_total_posts(root.getLeft().getLeft())));
        // Programming subtree: 72 + Java(30) + Programming(85) = 187
        check("Programming subtree total = 187",
                "187", String.valueOf(TreeTraversals.post_order_total_posts(root.getLeft())));
        // Full tree total = 462
        check("Full tree total posts = 462",
                "462", String.valueOf(TreeTraversals.post_order_total_posts(root)));
        check("Null node total = 0",
                "0", String.valueOf(TreeTraversals.post_order_total_posts(null)));

        // -------------------------------------------------
        printHeader("PART C — POST-ORDER COLLECT LEAVES");
        // -------------------------------------------------

        List<Category> leaves = TreeTraversals.post_order_collect_leaves(root);
        check("Leaf count = 5",
                "5", String.valueOf(leaves.size()));
        check("First leaf = Django",
                "Django(18)", leaves.get(0).toString());
        check("Last leaf = Graphics",
                "Graphics(22)", leaves.get(leaves.size() - 1).toString());
        // Single node is itself a leaf
        check("Single node is a leaf",
                "Solo(100)", TreeTraversals.post_order_collect_leaves(single).get(0).toString());

        // -------------------------------------------------
        printHeader("PART C — POST-ORDER AVERAGE DEPTH");
        // -------------------------------------------------

        // Leaf depths: Django=3, Flask=3, Java=2, UI/UX=2, Graphics=2
        // Average = (3+3+2+2+2)/5 = 12/5 = 2.4
        check("Average leaf depth = 2.4",
                "2.4", String.valueOf(TreeTraversals.post_order_average_depth(root)));
        check("Single node average depth = 0.0 (leaf at root)",
                "0.0", String.valueOf(TreeTraversals.post_order_average_depth(single)));
        check("Null tree average depth = 0.0",
                "0.0", String.valueOf(TreeTraversals.post_order_average_depth(null)));
        // Degenerate tree: only leaf is C at depth 2 → average = 2.0
        check("Degenerate tree average depth = 2.0",
                "2.0", String.valueOf(TreeTraversals.post_order_average_depth(degen)));

        // -------------------------------------------------
        printHeader("ANALYTICS — FIND MOST POPULAR CATEGORY");
        // -------------------------------------------------

        check("Most popular = Technology (150 posts)",
                "Technology(150)", TreeTraversals.find_most_popular_category(root).getValue().toString());
        // In Programming subtree: Programming(85) is highest
        check("Most popular in Programming subtree = Programming",
                "Programming(85)", TreeTraversals.find_most_popular_category(root.getLeft()).getValue().toString());
        check("Most popular in single node = that node",
                "Solo(100)", TreeTraversals.find_most_popular_category(single).getValue().toString());
        check("Most popular of null = null",
                "null", String.valueOf(TreeTraversals.find_most_popular_category(null)));

        // -------------------------------------------------
        printHeader("ANALYTICS — CATEGORY WITH MOST SUBCATEGORIES");
        // -------------------------------------------------

        // Programming has 2 children (Python, Java), Design has 2, Python has 2, Technology has 2
        // All tied at 2 — the function returns leftmost winner (Technology or Programming depending on traversal)
        CategoryNode mostSubs = TreeTraversals.category_with_most_subcategories(root);
        check("Most subcategories node has 2 children",
                "2", String.valueOf(
                        (mostSubs.getLeft() != null ? 1 : 0) + (mostSubs.getRight() != null ? 1 : 0)));
        check("Leaf node has 0 subcategories",
                "0", String.valueOf(
                        TreeTraversals.category_with_most_subcategories(
                                root.getLeft().getLeft().getLeft() // Django
                        ).getLeft() == null && TreeTraversals.category_with_most_subcategories(
                                root.getLeft().getLeft().getLeft()
                        ).getRight() == null ? "0" : "not 0"));

        // -------------------------------------------------
        printHeader("ITERATIVE TRAVERSALS");
        // -------------------------------------------------

        List<Category> iterIn  = TreeTraversals.iterative_in_order(root);
        List<Category> recurIn = TreeTraversals.in_order_collect(root);
        check("Iterative in-order matches recursive in-order",
                recurIn.toString(), iterIn.toString());

        List<Category> iterPre  = TreeTraversals.iterative_pre_order(root);
        List<String>   recurPre = TreeTraversals.pre_order_export(root, 0);
        // Just check first and last match
        check("Iterative pre-order first = Technology",
                "Technology(150)", iterPre.get(0).toString());
        check("Iterative pre-order last = Graphics",
                "Graphics(22)", iterPre.get(iterPre.size() - 1).toString());

        List<Category> iterPost  = TreeTraversals.iterative_post_order(root);
        List<Category> recurPost = TreeTraversals.post_order_collect(root);
        check("Iterative post-order matches recursive post-order",
                recurPost.toString(), iterPost.toString());

        System.out.println("\n" + "=".repeat(50));
        System.out.println("  ALL TESTS COMPLETE");
        System.out.println("=".repeat(50));
    }
}