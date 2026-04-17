import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ==============================================
        printHeader("PART 1 — MIDPOINT DISPLACEMENT");
        // ==============================================

        // Basic run — depth 3 on a flat line from (0,0) to (100,0)
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{0, 0}); // add start point manually
        Proceduralgeneration.midpoint_displacement(0, 0, 100, 0, 20, 3, points);

        // depth 3 produces 2^3 = 8 segments → 8 endpoints collected
        // plus the manually added start = 9 total points
        check("Depth 3 produces 8 collected endpoints (2^depth)",
                "8", String.valueOf(points.size() - 1)); // -1 for the start we added

        // All X values should be between 0 and 100
        boolean xInRange = points.stream().allMatch(p -> p[0] >= 0 && p[0] <= 100);
        check("All X values are within [0, 100]", xInRange);

        // With roughness=0 the Y midpoint should equal the average of endpoints
        // Use a fixed seed scenario: roughness=0 means no random offset
        List<double[]> flatPoints = new ArrayList<>();
        flatPoints.add(new double[]{0, 0});
        Proceduralgeneration.midpoint_displacement(0, 0, 100, 0, 0, 4, flatPoints);
        boolean allFlat = flatPoints.stream().allMatch(p -> p[1] == 0.0);
        check("roughness=0 keeps all Y values at 0 (no displacement)", allFlat);

        // depth=0 base case — should add just the end point and return
        List<double[]> depthZero = new ArrayList<>();
        Proceduralgeneration.midpoint_displacement(0, 0, 50, 0, 10, 0, depthZero);
        check("depth=0 adds exactly 1 point (the endpoint)",
                "1", String.valueOf(depthZero.size()));
        check("depth=0 endpoint X = 50",
                "50.0", String.valueOf(depthZero.get(0)[0]));

        // Higher depth should produce more points
        List<double[]> depth1 = new ArrayList<>();
        List<double[]> depth4 = new ArrayList<>();
        Proceduralgeneration.midpoint_displacement(0, 0, 100, 0, 10, 1, depth1);
        Proceduralgeneration.midpoint_displacement(0, 0, 100, 0, 10, 4, depth4);
        check("depth=4 produces more points than depth=1",
                "true", String.valueOf(depth4.size() > depth1.size()));

        // ==============================================
        printHeader("PART 2 — GENERATE TERRAIN");
        // ==============================================

        // size must be 2^n + 1. Use 5 (= 2^2 + 1) for a readable test
        double[][] terrain5 = Proceduralgeneration.generate_terrain(5, 1.0);

        check("Grid is 5x5",
                "5", String.valueOf(terrain5.length));
        check("Each row has 5 columns",
                "5", String.valueOf(terrain5[0].length));

        // Four corners must stay at 0 (set during initialisation, never touched again)
        check("Corner [0][0] = 0.0",
                "0.0", String.valueOf(terrain5[0][0]));
        check("Corner [0][4] = 0.0",
                "0.0", String.valueOf(terrain5[0][4]));
        check("Corner [4][0] = 0.0",
                "0.0", String.valueOf(terrain5[4][0]));
        check("Corner [4][4] = 0.0",
                "0.0", String.valueOf(terrain5[4][4]));

        // Centre point must have been set (diamond step fills it)
        // With roughness > 0 it will almost certainly not be 0
        // We just verify it was actually written (not left as default 0 from a failed run)
        // We verify the grid has no NaN values
        boolean noNaN = true;
        for (double[] row : terrain5) {
            for (double val : row) {
                if (Double.isNaN(val)) {
                    noNaN = false;
                    break;
                }
            }
        }
        check("No NaN values in 5x5 grid", noNaN);

        // Test with roughness = 0 — all interior points should be 0
        // because avg of zeros + 0 offset = 0
        double[][] flatTerrain = Proceduralgeneration.generate_terrain(5, 0.0);
        boolean allZero = true;
        for (double[] row : flatTerrain) {
            for (double val : row) {
                if (val != 0.0) {
                    allZero = false;
                    break;
                }
            }
        }
        check("roughness=0 produces all-zero terrain (no displacement)", allZero);

        // Larger grid test — 9x9 (2^3 + 1)
        double[][] terrain9 = Proceduralgeneration.generate_terrain(9, 2.0);
        check("9x9 grid has correct dimensions",
                "9", String.valueOf(terrain9.length));
        check("9x9 corners are still 0",
                "true", String.valueOf(
                        terrain9[0][0] == 0 && terrain9[0][8] == 0 &&
                                terrain9[8][0] == 0 && terrain9[8][8] == 0));

        boolean noNaN9 = true;
        for (double[] row : terrain9) {
            for (double val : row) {
                if (Double.isNaN(val)) {
                    noNaN9 = false;
                    break;
                }
            }
        }
        check("No NaN values in 9x9 grid", noNaN9);

        // Print a small terrain so you can visually verify it looks reasonable
        System.out.println("\n  Sample 5x5 terrain (roughness=1.0):");
        Proceduralgeneration.print_terrain(terrain5);

        // ==============================================
        printHeader("PART 3 — DETECT ARTIFACTS");
        // ==============================================

        // Manual grid with a known artifact
        // Row 0: 0, 0, 0
        // Row 1: 0, 100, 0   <- cell [1][1] jumps by 100 from neighbors
        // Row 2: 0, 0, 0
        double[][] manualGrid = {
                {0, 0, 0},
                {0, 100, 0},
                {0, 0, 0}
        };

        List<int[]> artifacts = Proceduralgeneration.detect_artifacts(manualGrid, 10.0);
        check("Spike of 100 detected with threshold 10",
                "true", String.valueOf(!artifacts.isEmpty()));

        // A perfectly flat grid should produce no artifacts at any threshold
        double[][] flatGrid = {
                {5, 5, 5},
                {5, 5, 5},
                {5, 5, 5}
        };
        List<int[]> noArtifacts = Proceduralgeneration.detect_artifacts(flatGrid, 1.0);
        check("Flat grid produces no artifacts",
                "0", String.valueOf(noArtifacts.size()));

        // A grid where every pair differs by exactly 5
        // Threshold > 5 → no artifacts
        // Threshold < 5 → artifacts found
        double[][] gradientGrid = {
                {0, 5, 10},
                {5, 10, 15},
                {10, 15, 20}
        };
        List<int[]> above = Proceduralgeneration.detect_artifacts(gradientGrid, 6.0);
        List<int[]> below = Proceduralgeneration.detect_artifacts(gradientGrid, 4.0);
        check("threshold=6 (above diff of 5) → no artifacts",
                "0", String.valueOf(above.size()));
        check("threshold=4 (below diff of 5) → artifacts found",
                "true", String.valueOf(!below.isEmpty()));

        // Edge case: single cell grid — no neighbours to compare
        double[][] singleCell = {{42.0}};
        List<int[]> singleArtifacts = Proceduralgeneration.detect_artifacts(singleCell, 0.0);
        check("1x1 grid produces no artifacts (no neighbours)",
                "0", String.valueOf(singleArtifacts.size()));

        // Test on generated terrain — with very low threshold almost everything
        // should be an artifact, with very high threshold nothing should be
        double[][] generated = Proceduralgeneration.generate_terrain(9, 2.0);
        List<int[]> highThreshold = Proceduralgeneration.detect_artifacts(generated, 1000.0);
        List<int[]> lowThreshold = Proceduralgeneration.detect_artifacts(generated, 0.001);
        check("Very high threshold → no artifacts on generated terrain",
                "0", String.valueOf(highThreshold.size()));
        check("Very low threshold → many artifacts on generated terrain",
                "true", String.valueOf(!lowThreshold.isEmpty()));

        System.out.println("\n" + "=".repeat(55));
        System.out.println("  ALL TESTS COMPLETE");
        System.out.println("=".repeat(55));

        Proceduralgeneration.draw_terrain(33);
    }

    static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("  " + title);
        System.out.println("=".repeat(55));
    }

    static void check(String name, boolean passed) {
        System.out.println((passed ? "[PASS] " : "[FAIL] ") + name);
    }

    static void check(String name, Object expected, Object actual) {
        boolean passed = expected.toString().equals(actual.toString());
        System.out.println((passed ? "[PASS] " : "[FAIL] ") + name);
        if (!passed) {
            System.out.println("       Expected : " + expected);
            System.out.println("       Got      : " + actual);
        }
    }
}