const { is_valid_coverage, find_minimum_coverage, find_fast_coverage } = require("./exercice1");

const graph = {
    A: ["B", "C"],
    B: ["A", "D", "E"],
    C: ["A", "F"],
    D: ["B"],
    E: ["B"],
    F: ["C"]
};


console.log("=== is_valid_coverage ===");

console.log(is_valid_coverage(["B", "C"], graph));

console.log(is_valid_coverage(["A"], graph));

console.log(is_valid_coverage(["A", "B", "C", "D", "E", "F"], graph));

console.log("=== find_minimum_coverage ===");

const [minSize, minSubset] = find_minimum_coverage(graph);
console.log("Minimum size:", minSize);
console.log("Minimum subset:", minSubset);

console.log("=== find_fast_coverage ===");

const [greedySize, greedySubset] = find_fast_coverage(graph);
console.log("Greedy size:", greedySize);
console.log("Greedy subset:", greedySubset);

console.log("=== Comparison ===");
console.log("Exact minimum:", minSize);
console.log("Greedy result:", greedySize);
console.log("Greedy is optimal:", greedySize === minSize);