public class Category {
    public String category_id;
    public String name;
    public int post_count;

    public Category(String category_id, String name, int post_count) {
        this.category_id = category_id;
        this.name = name;
        this.post_count = post_count;
    }

    @Override
    public String toString() {
        return name + "(" + post_count + ")";
    }
}