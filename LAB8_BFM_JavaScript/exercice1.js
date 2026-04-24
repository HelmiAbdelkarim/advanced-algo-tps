class UserBSTNode {
    constructor(user_id, name, friends_list) {
        this.user_id  = user_id;
        this.name     = name;
        this.friends  = friends_list;
        this.left     = null;
        this.right    = null;
    }
}

function insert(root, user_id, name, friends_list) {
    if (root === null) {
        return new UserBSTNode(user_id, name, friends_list);
    }

    if (user_id < root.user_id) {
        root.left  = insert(root.left,  user_id, name, friends_list);
    } else if (user_id > root.user_id) {
        root.right = insert(root.right, user_id, name, friends_list);
    }

    return root;
}

function find(root, user_id) {
    if (root === null) {
        return null;
    }

    if (user_id === root.user_id) {
        return root;
    } else if (user_id < root.user_id) {
        return find(root.left,  user_id);
    } else {
        return find(root.right, user_id);
    }
}

function inorder_traversal(root, result = []) {
    if (root === null) {
        return result;
    }

    inorder_traversal(root.left,  result);
    result.push(root.user_id);
    inorder_traversal(root.right, result);

    return result;
}

function getSuccessor(node) {
    let current = node.right;

    while (current.left !== null) {
        current = current.left;
    }

    return current;
}

function delete_node(root, user_id) {
    if (root === null) {
        return null;
    }

    if (user_id < root.user_id) {
        root.left  = delete_node(root.left,  user_id);
    } else if (user_id > root.user_id) {
        root.right = delete_node(root.right, user_id);
    } else {
        if (root.left === null)  return root.right;
        if (root.right === null) return root.left;

        const successor  = getSuccessor(root);
        root.user_id     = successor.user_id;
        root.name        = successor.name;
        root.friends     = successor.friends;
        root.right       = delete_node(root.right, successor.user_id);
    }

    return root;
}

function get_height(root) {
    if (root === null) {
        return -1;
    }

    const left_height  = get_height(root.left);
    const right_height = get_height(root.right);

    return Math.max(left_height, right_height) + 1;
}

function is_balanced(root) {
    if (root === null) {
        return true;
    }

    const left_height  = get_height(root.left);
    const right_height = get_height(root.right);

    if (Math.abs(left_height - right_height) > 1) {
        return false;
    }

    return is_balanced(root.left) && is_balanced(root.right);
}

function get_leaf_count(root) {
    if (root === null) {
        return 0;
    }

    if (root.left === null && root.right === null) {
        return 1;
    }

    return get_leaf_count(root.left) + get_leaf_count(root.right);
}

function suggest_friends(root, user_id, max_suggestions = 5) {
    const user = find(root, user_id);
    if (user === null) return null;

    const direct_friends = new Set(user.friends);
    const fof_count      = new Map();

    for (const friend_id of user.friends) {
        const friend = find(root, friend_id);
        if (friend === null) continue;

        for (const fof_id of friend.friends) {
            if (fof_id !== user_id && !direct_friends.has(fof_id)) {
                fof_count.set(fof_id, (fof_count.get(fof_id) || 0) + 1);
            }
        }
    }

    const sorted = [...fof_count.entries()]
        .sort((a, b) => b[1] - a[1])
        .slice(0, max_suggestions)
        .map(entry => entry[0]);

    return sorted;
}

// Test

let root = null;

root = insert(root, 5, "Alice", [3, 7]);
root = insert(root, 3, "Bob", [5, 2]);
root = insert(root, 7, "Charlie", [5, 8]);
root = insert(root, 2, "Diana", [3]);
root = insert(root, 8, "Eve", [7]);
root = insert(root, 10, "Frank", [8]);

console.log("Inorder:", inorder_traversal(root));
console.log("Find 7:", find(root, 7));
console.log("Height:", get_height(root));
console.log("Balanced:", is_balanced(root));
console.log("Leaf count:", get_leaf_count(root));
console.log("Suggestions:", suggest_friends(root, 5));

root = delete_node(root, 7);
console.log("After delete 7:", inorder_traversal(root));