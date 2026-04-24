const {
    dfs_recursive,
    dfs_iterative,
    find_connected_components,
    is_connected,
    has_path,
    find_path,
    get_connected_components_sizes,
    find_largest_component,
    find_isolated_users,
} = require("./exercice2.js");

const L_chain = [[1], [0, 2], [1]];
const n_chain = 3;

const L_disconnected = [[1], [0], [], [4], [3]];
const n_disconnected = 5;

const visited1 = new Set();
console.log("dfs_recursive(0, visited, [], L_chain): " + JSON.stringify(dfs_recursive(0, visited1, [], L_chain)));
console.log("dfs_iterative(0, L_chain): " + JSON.stringify(dfs_iterative(0, L_chain)));

console.log("find_connected_components(L_chain, n_chain): " + JSON.stringify(find_connected_components(L_chain, n_chain)));
console.log("is_connected(L_chain, n_chain): " + is_connected(L_chain, n_chain));

console.log("find_connected_components(L_disconnected, n_disconnected): " + JSON.stringify(find_connected_components(L_disconnected, n_disconnected)));
console.log("is_connected(L_disconnected, n_disconnected): " + is_connected(L_disconnected, n_disconnected));

console.log("has_path(0, 2, L_chain): " + has_path(0, 2, L_chain));
console.log("has_path(0, 3, L_disconnected): " + has_path(0, 3, L_disconnected));
console.log("find_path(0, 2, L_chain): " + JSON.stringify(find_path(0, 2, L_chain)));
console.log("find_path(0, 3, L_disconnected): " + find_path(0, 3, L_disconnected));

console.log("get_connected_components_sizes(L_disconnected, n_disconnected): " + JSON.stringify(get_connected_components_sizes(L_disconnected, n_disconnected)));
console.log("find_largest_component(L_disconnected, n_disconnected): " + JSON.stringify(find_largest_component(L_disconnected, n_disconnected)));
console.log("find_isolated_users(L_disconnected, n_disconnected): " + JSON.stringify(find_isolated_users(L_disconnected, n_disconnected)));
