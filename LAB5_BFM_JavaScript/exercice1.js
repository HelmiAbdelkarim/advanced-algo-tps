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

    if (leftSubTreeHeight !== -1) {
        return leftSubTreeHeight;
    }

    let rightSubTreeHeight = calculate_node_height(node.right, target_id);

    if (rightSubTreeHeight !== -1) {
        return rightSubTreeHeight;
    }

    return -1;
};

function count_nodes(node) {
    
    if (node === null) {
        return 0;
    }
    return 1 + count_nodes(node.left) + count_nodes(node.right);

}

function count_leaves(node) {
    if (node === null) {
        return 0;
    }
    if (node.left === null && node.right === null) {
        return 1;
    }
    return count_leaves(node.left) + count_leaves(node.right);
}

function is_balanced(node) {

    if (node === null) {
        return true;
    }

    let leftSubTreeHeight = calculate_height(node.left);
    let rightSubTreeHeight = calculate_height(node.right);

    if (Math.abs(leftSubTreeHeight - rightSubTreeHeight) > 1) {
        return false;
    }

    return is_balanced(node.left) && is_balanced(node.right);
}

function is_full_binary_tree(node) {
    if (node === null) {
        return true;
    }
    if (node.left === null && node.right === null) {
        return true;
    }
    return is_full_binary_tree(node.left) && is_full_binary_tree(node.right);
}

function is_perfect_binary_tree(node) {
    if (node === null) {
        return true;
    }
    if (node.left === null && node.right === null) {
        return true;
    }
    return is_perfect_binary_tree(node.left) && is_perfect_binary_tree(node.right);
}

function is_complete_binary_tree(node) {
    if (node === null) {
        return true;
    }
    if (node.left === null && node.right === null) {
        return true;
    }
    return is_complete_binary_tree(node.left) && is_complete_binary_tree(node.right);
}

export { 
    CategoryNode,
    calculate_height,
    calculate_node_height,
    count_nodes,
    count_leaves,
    is_balanced,
    is_full_binary_tree,
    is_perfect_binary_tree,
    is_complete_binary_tree
};