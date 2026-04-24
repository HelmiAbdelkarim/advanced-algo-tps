function dfs_recursive(start_user, visited, path, L) {
    visited.add(start_user);
    path.push(start_user);

    const neighbours = L[start_user] ?? [];
    for (const neighbour of neighbours) {
        if (!visited.has(neighbour)) {
            dfs_recursive(neighbour, visited, path, L);
        }
    }

    return path;
}

function dfs_iterative(start_user, L) {
    const stack = [];
    const visited = new Set();
    const path = [];

    stack.push(start_user);

    while (stack.length > 0) {
        const current = stack.pop();

        if (!visited.has(current)) {
            visited.add(current);
            path.push(current);

            const neighbours = L[current] ?? [];
            for (const neighbour of neighbours) {
                if (!visited.has(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }
    }

    return path;
}

function find_connected_components(L, n) {
    const visited = new Set();
    const components = [];

    for (let i = 0; i < n; i++) {
        if (!visited.has(i)) {
            const component = dfs_recursive(i, visited, [], L);
            components.push(component);
        }
    }

    return components;
}

function is_connected(L, n) {
    const components = find_connected_components(L, n);
    return components.length === 1;
}

function has_path(start_user, target_user, L) {
    const stack = [];
    const visited = new Set();

    stack.push(start_user);

    while (stack.length > 0) {
        const current = stack.pop();

        if (current === target_user) {
            return true;
        }

        if (!visited.has(current)) {
            visited.add(current);

            const neighbours = L[current] ?? [];
            for (const neighbour of neighbours) {
                if (!visited.has(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }
    }

    return false;
}

function find_path(start_user, target_user, L) {
    const stack = [];
    const visited = new Set();
    const parent = Object.create(null);

    stack.push(start_user);
    parent[start_user] = null;

    while (stack.length > 0) {
        const current = stack.pop();

        if (current === target_user) {
            const path = [];
            let node = target_user;
            while (node !== null && node !== undefined) {
                path.push(node);
                node = parent[node];
            }
            return path.reverse();
        }

        if (!visited.has(current)) {
            visited.add(current);

            const neighbours = L[current] ?? [];
            for (const neighbour of neighbours) {
                if (!visited.has(neighbour) && !(neighbour in parent)) {
                    parent[neighbour] = current;
                    stack.push(neighbour);
                }
            }
        }
    }

    return null;
}

function get_connected_components_sizes(L, n) {
    const components = find_connected_components(L, n);
    return components.map((c) => c.length);
}

function find_largest_component(L, n) {
    const components = find_connected_components(L, n);
    let largest = [];

    for (const component of components) {
        if (component.length > largest.length) {
            largest = component;
        }
    }

    return largest;
}

function find_isolated_users(L, n) {
    const isolated = [];

    for (let i = 0; i < n; i++) {
        const neighbours = L[i] ?? [];
        if (neighbours.length === 0) {
            isolated.push(i);
        }
    }

    return isolated;
}

export {
    dfs_recursive,
    dfs_iterative,
    find_connected_components,
    is_connected,
    has_path,
    find_path,
    get_connected_components_sizes,
    find_largest_component,
    find_isolated_users,
};
