import java.util.*;

public class Main {


    private static int passed = 0;
    private static int failed = 0;

    private static void assertTrue(String name, boolean condition) {
        if (condition) {
            System.out.println("  ✓ " + name);
            passed++;
        } else {
            System.out.println("  ✗ FAIL: " + name);
            failed++;
        }
    }

    private static void assertEquals(String name, int expected, int actual) {
        if (expected == actual) {
            System.out.println("  ✓ " + name);
            passed++;
        } else {
            System.out.println("  ✗ FAIL: " + name +
                    " | expected=" + expected + " actual=" + actual);
            failed++;
        }
    }

    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf( "║  %-36s║%n", title);
        System.out.println("╚══════════════════════════════════════╝");
    }


    /** Computes the actual total influence of a result (sanity check). */
    private static int actualInfluence(InfluenceMaximizer.Result r, int[] influences) {
        int sum = 0;
        for (int idx : r.selectedUsers) sum += influences[idx];
        return sum;
    }


    static void testIsWithinBudget() {
        printHeader("isWithinBudget");

        int[] costs = {3, 5, 2, 7};

        // Exact budget
        assertTrue("exact budget fits",
                InfluenceMaximizer.isWithinBudget(List.of(0, 2), costs, 5));   // 3+2=5

        // Under budget
        assertTrue("under budget fits",
                InfluenceMaximizer.isWithinBudget(List.of(0), costs, 10));     // 3 ≤ 10

        // Over budget
        assertTrue("over budget rejected",
                !InfluenceMaximizer.isWithinBudget(List.of(1, 3), costs, 10)); // 5+7=12 > 10

        // Empty selection always fits
        assertTrue("empty selection always fits",
                InfluenceMaximizer.isWithinBudget(List.of(), costs, 0));
    }

    static void testMaximizeReach() {
        printHeader("maximizeReach (DP – optimal)");

        // ── TC1: Basic example ──────────────────────────────────────────────
        {
            int budget     = 10;
            int[] costs    = {3, 4, 5, 6};
            int[] infl     = {4, 5, 6, 7};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            // Optimal: pick index 1 (cost 4, infl 5) + index 3 (cost 6, infl 7) = 12, cost 10
            // Other combos: idx0+idx3 = infl 11, idx1+idx2 = infl 11 → 12 is truly optimal
            assertEquals("TC1 – total influence", 12, r.totalInfluence);
            assertTrue  ("TC1 – within budget",
                    InfluenceMaximizer.isWithinBudget(r.selectedUsers, costs, budget));
            assertEquals("TC1 – internal consistency",
                    r.totalInfluence, actualInfluence(r, infl));
        }

        // ── TC2: Zero budget ───────────────────────────────────────────────
        {
            int budget     = 0;
            int[] costs    = {1, 2, 3};
            int[] infl     = {10, 20, 30};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            assertEquals("TC2 – zero budget → influence = 0", 0, r.totalInfluence);
            assertTrue  ("TC2 – no users selected", r.selectedUsers.isEmpty());
        }

        // ── TC3: All items affordable ──────────────────────────────────────
        {
            int budget     = 100;
            int[] costs    = {2, 3, 4};
            int[] infl     = {10, 20, 30};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            assertEquals("TC3 – can take all → influence = 60", 60, r.totalInfluence);
            assertEquals("TC3 – all 3 users selected", 3, r.selectedUsers.size());
        }

        // ── TC4: Only one item fits ────────────────────────────────────────
        {
            int budget     = 3;
            int[] costs    = {3, 5, 7};
            int[] infl     = {4, 8, 15};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            assertEquals("TC4 – only cheapest fits", 4, r.totalInfluence);
        }

        // ── TC5: No item fits ─────────────────────────────────────────────
        {
            int budget     = 2;
            int[] costs    = {5, 6, 7};
            int[] infl     = {10, 20, 30};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            assertEquals("TC5 – nothing fits → 0", 0, r.totalInfluence);
            assertTrue  ("TC5 – no users selected", r.selectedUsers.isEmpty());
        }

        {
            int budget     = 7;
            int[] costs    = {1, 3, 4, 5};
            int[] infl     = {1, 4, 5, 7};

            InfluenceMaximizer.Result dpResult     = InfluenceMaximizer.maximizeReach(budget, costs, infl);
            InfluenceMaximizer.Result greedyResult = InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertEquals("TC6 – DP finds true optimum (9)", 9, dpResult.totalInfluence);
            assertEquals("TC6 – greedy sub-optimal (8)",    8, greedyResult.totalInfluence);
            assertTrue  ("TC6 – DP ≥ greedy",
                    dpResult.totalInfluence >= greedyResult.totalInfluence);
        }

        // ── TC7: Single user, affordable ──────────────────────────────────
        {
            int budget     = 5;
            int[] costs    = {5};
            int[] infl     = {42};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            assertEquals("TC7 – single affordable user", 42, r.totalInfluence);
            assertEquals("TC7 – exactly one selected",    1, r.selectedUsers.size());
        }

        // ── TC8: Single user, not affordable ──────────────────────────────
        {
            int budget     = 4;
            int[] costs    = {5};
            int[] infl     = {42};

            InfluenceMaximizer.Result r = InfluenceMaximizer.maximizeReach(budget, costs, infl);

            assertEquals("TC8 – single too-expensive user", 0, r.totalInfluence);
            assertTrue  ("TC8 – none selected", r.selectedUsers.isEmpty());
        }
    }

    static void testFastAlternativeStrategy() {
        printHeader("fastAlternativeStrategy (Greedy)");

        // ── TC1: Standard case ─────────────────────────────────────────────
        {
            int budget     = 10;
            int[] costs    = {2, 4, 6};
            int[] infl     = {6, 8, 9};
            // ratios: 3.0, 2.0, 1.5 → pick idx0 (cost 2), idx1 (cost 4), then
            // remaining=4, idx2 cost 6 > 4 → skip
            // total = 6+8 = 14

            InfluenceMaximizer.Result r =
                    InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertEquals("TC1 – total influence", 14, r.totalInfluence);
            assertTrue  ("TC1 – within budget",
                    InfluenceMaximizer.isWithinBudget(r.selectedUsers, costs, budget));
            assertEquals("TC1 – internal consistency",
                    r.totalInfluence, actualInfluence(r, infl));
        }

        // ── TC2: Zero budget ───────────────────────────────────────────────
        {
            int budget     = 0;
            int[] costs    = {5, 3};
            int[] infl     = {10, 6};

            InfluenceMaximizer.Result r =
                    InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertEquals("TC2 – zero budget", 0, r.totalInfluence);
            assertTrue  ("TC2 – nothing selected", r.selectedUsers.isEmpty());
        }

        // ── TC3: Tie-breaking (same ratio) ────────────────────────────────
        {
            int budget     = 5;
            int[] costs    = {2, 4};
            int[] infl     = {4, 8};   // both ratio = 2.0

            InfluenceMaximizer.Result r =
                    InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertTrue("TC3 – within budget",
                    InfluenceMaximizer.isWithinBudget(r.selectedUsers, costs, budget));
            // Either outcome is acceptable; just verify consistency
            assertEquals("TC3 – consistency",
                    r.totalInfluence, actualInfluence(r, infl));
        }

        // ── TC4: Only highest-ratio item picked ───────────────────────────
        {
            int budget     = 3;
            int[] costs    = {1, 5, 5};
            int[] infl     = {10, 4, 4};  // ratio: 10, 0.8, 0.8

            InfluenceMaximizer.Result r =
                    InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertEquals("TC4 – best ratio item selected", 10, r.totalInfluence);
        }

        // ── TC5: All items picked when budget allows ───────────────────────
        {
            int budget     = 20;
            int[] costs    = {1, 2, 3};
            int[] infl     = {5, 10, 15};

            InfluenceMaximizer.Result r =
                    InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertEquals("TC5 – all selected", 30, r.totalInfluence);
            assertEquals("TC5 – 3 users",       3, r.selectedUsers.size());
        }

        // ── TC6: No duplicates in selection ───────────────────────────────
        {
            int budget     = 50;
            int[] costs    = {10, 20, 15};
            int[] infl     = {30, 50, 40};

            InfluenceMaximizer.Result r =
                    InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            Set<Integer> uniqueSet = new HashSet<>(r.selectedUsers);
            assertTrue("TC6 – no duplicate selections",
                    uniqueSet.size() == r.selectedUsers.size());
        }
    }

    static void testDPAlwaysOptimal() {
        printHeader("DP ≥ Greedy (cross-validation)");

        int[][][] testCases = {
                // {budget}, {costs...}, {influences...}
                {{10}, {3, 4, 5, 6},    {4, 5, 6, 7}},
                {{7},  {1, 3, 4, 5},    {1, 4, 5, 7}},
                {{15}, {2, 5, 8, 3, 7}, {3, 8, 10, 4, 9}},
                {{6},  {1, 2, 3, 4},    {1, 6, 10, 16}},
                {{50}, {10, 20, 30},    {60, 100, 120}},
        };

        for (int t = 0; t < testCases.length; t++) {
            int   budget    = testCases[t][0][0];
            int[] costs     = testCases[t][1];
            int[] infl      = testCases[t][2];

            InfluenceMaximizer.Result dp     = InfluenceMaximizer.maximizeReach(budget, costs, infl);
            InfluenceMaximizer.Result greedy = InfluenceMaximizer.fastAlternativeStrategy(budget, costs, infl);

            assertTrue("TC" + (t + 1) + " – DP ≥ Greedy (" +
                            dp.totalInfluence + " ≥ " + greedy.totalInfluence + ")",
                    dp.totalInfluence >= greedy.totalInfluence);

            assertTrue("TC" + (t + 1) + " – DP within budget",
                    InfluenceMaximizer.isWithinBudget(dp.selectedUsers, costs, budget));

            assertTrue("TC" + (t + 1) + " – Greedy within budget",
                    InfluenceMaximizer.isWithinBudget(greedy.selectedUsers, costs, budget));
        }
    }

    public static void main(String[] args) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  InfluenceMaximizer – Test Suite");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        testIsWithinBudget();
        testMaximizeReach();
        testFastAlternativeStrategy();
        testDPAlwaysOptimal();

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("  Results: %d passed, %d failed%n", passed, failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (failed > 0) System.exit(1);
    }
}