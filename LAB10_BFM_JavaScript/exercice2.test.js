const { is_within_budget, maximize_reach_exact, maximize_reach_greedy } = require('./exercise2');

const costs   = [6, 5, 5, 3, 2];
const reaches = [9, 7, 7, 4, 3];
const budget  = 10;

console.log("=== is_within_budget ===");

console.log(is_within_budget([1, 2], costs, budget) === true,
    "Selected users 1+2 cost 10, within budget");

console.log(is_within_budget([0, 1], costs, budget) === false,
    "Selected users 0+1 cost 11, over budget");

console.log(is_within_budget([], costs, budget) === true,
    "Empty selection costs 0, within budget");

console.log(is_within_budget([4], costs, 1) === false,
    "User 4 costs 2, budget is 1, over budget");


console.log("\n=== maximize_reach_exact ===");

const [exact_reach, exact_users] = maximize_reach_exact(budget, costs, reaches);

console.log(exact_reach === 14,
    "Exact max reach should be 14, got " + exact_reach);

console.log(is_within_budget(exact_users, costs, budget) === true,
    "Exact selected users must be within budget");

const [zero_reach, zero_users] = maximize_reach_exact(0, costs, reaches);
console.log(zero_reach === 0,
    "Budget 0 should give reach 0, got " + zero_reach);

const [single_reach] = maximize_reach_exact(2, [2], [5]);
console.log(single_reach === 5,
    "Single user within budget should give reach 5, got " + single_reach);

const [none_reach] = maximize_reach_exact(1, costs, reaches);
console.log(none_reach === 0,
    "No user fits budget 1, reach should be 0, got " + none_reach);


console.log("\n=== maximize_reach_greedy ===");

const [greedy_reach, greedy_users] = maximize_reach_greedy(budget, costs, reaches);

console.log(is_within_budget(greedy_users, costs, budget) === true,
    "Greedy selected users must be within budget");

console.log(greedy_reach <= exact_reach,
    "Greedy reach should be <= exact reach, got greedy=" + greedy_reach + " exact=" + exact_reach);

const [greedy_zero] = maximize_reach_greedy(0, costs, reaches);
console.log(greedy_zero === 0,
    "Greedy budget 0 should give reach 0, got " + greedy_zero);

const [greedy_all] = maximize_reach_greedy(1000, costs, reaches);
console.log(greedy_all === reaches.reduce((a, b) => a + b, 0),
    "Large budget should select all users");