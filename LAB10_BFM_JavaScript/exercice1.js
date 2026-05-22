function is_within_budget(selection, costs, budget) {
    let total_cost = 0;

    for (const user_index of selection) {
        total_cost += costs[user_index];
    }

    return total_cost <= budget;
}


function maximize_reach_exact(budget, costs, reaches) {
    const n  = costs.length;

    const dp = Array.from({ length: n + 1 }, () => new Array(budget + 1).fill(0));

    for (let i = 1; i <= n; i++) {
        for (let j = 0; j <= budget; j++) {
            const exclude_value = dp[i-1][j];

            if (costs[i-1] <= j) {
                const keep_value = reaches[i-1] + dp[i-1][j - costs[i-1]];
                dp[i][j] = Math.max(keep_value, exclude_value);
            } else {
                dp[i][j] = exclude_value;
            }
        }
    }

    const selected_users = [];
    let j = budget;

    for (let i = n; i >= 1; i--) {
        if (dp[i][j] !== dp[i-1][j]) {
            selected_users.push(i - 1);
            j -= costs[i-1];
        }
    }

    return [dp[n][budget], selected_users];
}


function maximize_reach_greedy(budget, costs, reaches) {
    const n      = costs.length;
    const ratios = [];

    for (let i = 0; i < n; i++) {
        ratios.push([reaches[i] / costs[i], i]);
    }

    ratios.sort((a, b) => b[0] - a[0]);

    let total_reach      = 0;
    let current_budget   = budget;
    const selected_users = [];

    for (const [ratio, i] of ratios) {
        if (costs[i] <= current_budget) {
            selected_users.push(i);
            total_reach    += reaches[i];
            current_budget -= costs[i];
        }
    }

    return [total_reach, selected_users];
}


module.exports = { is_within_budget, maximize_reach_exact, maximize_reach_greedy };