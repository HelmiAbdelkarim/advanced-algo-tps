public class Main {

    public static void main(String[] args) {

        // =========================================================
        // BUILD THE LAB TREE
        // =========================================================
        //
        //           Technology(150)
        //          /               \
        //    Programming(85)      Design(65)
        //       /        \         /       \
        //   Python(42)  Java(30) UI/UX(38) Graphics(22)
        //    /     \
        // Django(18) Flask(12)

        CategoryNode technology  = new CategoryNode(new Category("1", "Technology",  150));
        CategoryNode programming = new CategoryNode(new Category("2", "Programming",  85));
        CategoryNode design      = new CategoryNode(new Category("3", "Design",        65));
        CategoryNode python      = new CategoryNode(new Category("4", "Python",        42));
        CategoryNode java        = new CategoryNode(new Category("5", "Java",          30));
        CategoryNode uiux        = new CategoryNode(new Category("6", "UI/UX",         38));
        CategoryNode graphics    = new CategoryNode(new Category("7", "Graphics",      22));
        CategoryNode django      = new CategoryNode(new Category("8", "Django",        18));
        CategoryNode flask       = new CategoryNode(new Category("9", "Flask",         12));

        technology.setLeft(programming);
        technology.setRight(design);
        programming.setLeft(python);
        programming.setRight(java);
        design.setLeft(uiux);
        design.setRight(graphics);
        python.setLeft(django);
        python.setRight(flask);

        CategoryNode root = technology;

        // =========================================================
        // PART A — IN-ORDER TRAVERSAL
        // =========================================================

        System.out.println("=== PART A: IN-ORDER TRAVERSAL ===\n");

        System.out.print("in_order_collect: ");
        System.out.println(TreeTraversals.in_order_collect(root));

        System.out.println("in_order_accumulate_posts: "
                + TreeTraversals.in_order_accumulate_posts(root) + " total posts");

        System.out.println("in_order_find_kth(1): "
                + TreeTraversals.in_order_find_kth(1, root));
        System.out.println("in_order_find_kth(6): "
                + TreeTraversals.in_order_find_kth(6, root));
        System.out.println("in_order_find_kth(9): "
                + TreeTraversals.in_order_find_kth(9, root));

        // =========================================================
        // PART B — PRE-ORDER TRAVERSAL
        // =========================================================

        System.out.println("\n=== PART B: PRE-ORDER TRAVERSAL ===\n");

        System.out.println("pre_order_export:");
        for (String line : TreeTraversals.pre_order_export(root, 0)) {
            System.out.println(line);
        }

        CategoryNode copy = TreeTraversals.pre_order_copy(root);
        System.out.println("\npre_order_copy root: " + copy.getValue());
        System.out.println("pre_order_copy left child: " + copy.getLeft().getValue());

        System.out.println("\npre_order_serialize: "
                + TreeTraversals.pre_order_serialize(root));

        // =========================================================
        // PART C — POST-ORDER TRAVERSAL
        // =========================================================

        System.out.println("\n=== PART C: POST-ORDER TRAVERSAL ===\n");

        System.out.print("post_order_collect: ");
        System.out.println(TreeTraversals.post_order_collect(root));

        System.out.println("post_order_total_posts (full tree): "
                + TreeTraversals.post_order_total_posts(root));
        System.out.println("post_order_total_posts (Python subtree): "
                + TreeTraversals.post_order_total_posts(python));
        System.out.println("post_order_total_posts (Programming subtree): "
                + TreeTraversals.post_order_total_posts(programming));

        System.out.print("post_order_collect_leaves: ");
        System.out.println(TreeTraversals.post_order_collect_leaves(root));

        System.out.println("post_order_average_depth: "
                + TreeTraversals.post_order_average_depth(root));

        // =========================================================
        // ANALYTICS
        // =========================================================

        System.out.println("\n=== ANALYTICS ===\n");

        System.out.println("find_most_popular_category: "
                + TreeTraversals.find_most_popular_category(root).getValue());

        System.out.println("category_with_most_subcategories: "
                + TreeTraversals.category_with_most_subcategories(root).getValue());

        // =========================================================
        // ITERATIVE TRAVERSALS
        // =========================================================

        System.out.println("\n=== ITERATIVE TRAVERSALS ===\n");

        System.out.print("iterative_in_order:   ");
        System.out.println(TreeTraversals.iterative_in_order(root));

        System.out.print("iterative_pre_order:  ");
        System.out.println(TreeTraversals.iterative_pre_order(root));

        System.out.print("iterative_post_order: ");
        System.out.println(TreeTraversals.iterative_post_order(root));
    }
}