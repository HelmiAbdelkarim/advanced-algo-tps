public class CategoryNode {
    private Category value;
    private CategoryNode left;
    private CategoryNode right;

    public CategoryNode(Category value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Observers
    public Category getValue()      { return value; }
    public CategoryNode getLeft()   { return left; }
    public CategoryNode getRight()  { return right; }

    // Setters (needed for tree construction and pre_order_copy)
    public void setLeft(CategoryNode left)   { this.left = left; }
    public void setRight(CategoryNode right) { this.right = right; }
}