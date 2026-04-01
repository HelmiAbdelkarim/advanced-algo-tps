class CategoryNode {
    constructor(category_id, name, post_count) {
        this.category_id = category_id;
        this.name = name;
        this.post_count = post_count;
        this.left = null;
        this.right = null;
    }
};

function calculate_height(node) {
    if (node === null) {
        return -1;
    }

    let leftSubTreeHeight = calculate_height(node.left);
    let rightSubTreeHeight = calculate_height(node.right);

    return Math.max(leftSubTreeHeight, rightSubTreeHeight) + 1;
};

function calculate_node_height(node, target_id) {
    if (node === null) {
        return -1;
    }

    if (node.category_id === target_id) {
        return calculate_height(node);
    }

    let leftSubTreeHeight = calculate_node_height(node.left, target_id);
    let rightSubTreeHeight = calculate_node_height(node.right, target_id);

    if (leftSubTreeHeight !== -1) {
      return leftSubTreeHeight;
    }

    if (rightSubTreeHeight !== -1) {
       return rightSubTreeHeight;
    }

    return;
};

function count_nodes(node) {
    
    if (node === null) {
        return 0;
    }
    return 1 + count_nodes(node.left) + count_nodes(node.right);

}

export { CategoryNode, calculate_height, calculate_node_height, count_nodes };