const {
    CategoryNode,
    calculate_height,
    calculate_node_height,
    count_nodes,
    is_balanced
} = require('./exercice1.js');


let root = new CategoryNode(1, "Technology", 150);
let node1 = new CategoryNode(3, "Design", 65);
let node2 = new CategoryNode(2, "Programming", 85);
let node3 = new CategoryNode(4, "Python", 42);
let node4 = new CategoryNode(5, "Django", 18);
let node5 = new CategoryNode(6, "Flask", 12);
let node6 = new CategoryNode(7, "Java", 30);
let node7 = new CategoryNode(8, "UI/UX", 38);
let node8 = new CategoryNode(9, "Graphics", 22);

root.left = node1;
root.right = node2;
node1.left = node8;
node1.right = node7;
node2.left = node5;
node2.right = node6;

console.log("calculate_height(root): " + calculate_height(root));
console.log("calculate_node_height(root, 9): " + calculate_node_height(root, 9));
console.log("calculate_node_height(root, 4): " + calculate_node_height(root, 4));
console.log("count_nodes(root): " + count_nodes(root));
console.log("is_balanced(root): " + is_balanced(root));




