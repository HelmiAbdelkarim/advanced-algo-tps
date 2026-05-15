function is_valid_coverage(selected_users, graph) {
    const selectedSet = new Set(selected_users);
    const covered = new Set();

    for (const user of selectedSet) {
        covered.add(user);
    }

    for (const user of selectedSet) {
        for (const neighbor of graph[user]) {
            covered.add(neighbor);
        }
    }

    for (const node of Object.keys(graph)) {
        if (!covered.has(node)) {
            return false;
        }
    }

    return true;
}

function find_minimum_coverage(graph) {
    const nodes = Object.keys(graph);
    const n = nodes.length;
    let bestSize = n + 1;
    let bestSubset = [];

    for (let mask = 0; mask < Math.pow(2, n); mask++) {
        const subset = [];

        for (let i = 0; i < n; i++) {
            if (mask & (1 << i)) {
                subset.push(nodes[i]);
            }
        }

        if (subset.length < bestSize) {
            if (is_valid_coverage(subset, graph)) {
                bestSize = subset.length;
                bestSubset = subset;
            }
        }
    }

    return [bestSize, bestSubset];
}

function find_fast_coverage(graph) {
    const covered = new Set();
    const selected = [];
    const nodes = Object.keys(graph);
    const total = nodes.length;

    while (covered.size < total) {
        let bestNode = null;
        let bestGain = -1;

        for (const node of nodes) {
            if (!covered.has(node)) {
                let gain = 0;

                if (!covered.has(node)) {
                    gain += 1;
                }
                for (const neighbor of graph[node]) {
                    if (!covered.has(neighbor)) {
                        gain += 1;
                    }
                }

                if (gain > bestGain) {
                    bestGain = gain;
                    bestNode = node;
                }
            }
        }

        selected.push(bestNode);
        covered.add(bestNode);
        for (const neighbor of graph[bestNode]) {
            covered.add(neighbor);
        }
    }

    return [selected.length, selected];
}

module.exports = { is_valid_coverage, find_minimum_coverage, find_fast_coverage };