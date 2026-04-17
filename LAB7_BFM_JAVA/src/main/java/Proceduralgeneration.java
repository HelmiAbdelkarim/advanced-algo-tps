import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Proceduralgeneration {

    private static final Random random = new Random();

    // Returns a random value between -1 and 1
    private static double randomOffset() {
        return random.nextDouble() * 2 - 1;
    }

    // =========================================================
    // PART 1 — MIDPOINT DISPLACEMENT
    // =========================================================

    public static void midpoint_displacement(double x1, double y1,
                                             double x2, double y2,
                                             double roughness, int depth,
                                             List<double[]> result) {
        if (depth == 0) {
            result.add(new double[]{x2, y2});
            return;
        }

        double midX = (x1 + x2) / 2.0;
        double offset = roughness * randomOffset();
        double midY   = ((y1 + y2) / 2.0) + offset;

        midpoint_displacement(x1, y1, midX, midY, roughness / 2, depth - 1, result);
        midpoint_displacement(midX, midY, x2, y2, roughness / 2, depth - 1, result);
    }

    // =========================================================
    // PART 2 — GENERATE TERRAIN (Diamond-Square Algorithm)
    // =========================================================

    public static double[][] generate_terrain(int size, double roughness) {
        double[][] grid = new double[size][size];

        grid[0][0]           = 0;
        grid[0][size-1]      = 0;
        grid[size-1][0]      = 0;
        grid[size-1][size-1] = 0;

        int    step               = size - 1;
        double current_roughness  = roughness;

        while (step > 1) {
            int half_step = step / 2;

            // DIAMOND STEP
            for (int y = 0; y < size - 1; y += step) {
                for (int x = 0; x < size - 1; x += step) {
                    double avg = (grid[x][y]
                            + grid[x + step][y]
                            + grid[x][y + step]
                            + grid[x + step][y + step]) / 4.0;
                    grid[x + half_step][y + half_step] = avg + (current_roughness * randomOffset());
                }
            }

            // SQUARE STEP
            for (int y = 0; y < size; y += half_step) {
                int start_x = ((y / half_step) % 2 == 0) ? half_step : 0;

                for (int x = start_x; x < size; x += step) {
                    double avg = calculate_average_of_neighbors(grid, x, y, half_step, size);
                    grid[x][y] = avg + (current_roughness * randomOffset());
                }
            }

            step              = half_step;
            current_roughness = current_roughness / 2.0;
        }

        return grid;
    }

    private static double calculate_average_of_neighbors(double[][] grid, int x, int y, int half_step, int size) {
        double sum   = 0;
        int    count = 0;

        if (y - half_step >= 0)    { sum += grid[x][y - half_step]; count++; }  // top
        if (y + half_step < size)  { sum += grid[x][y + half_step]; count++; }  // bottom
        if (x - half_step >= 0)    { sum += grid[x - half_step][y]; count++; }  // left
        if (x + half_step < size)  { sum += grid[x + half_step][y]; count++; }  // right

        return count == 0 ? 0 : sum / count;
    }

    // =========================================================
    // PART 3 — DETECT ARTIFACTS
    // =========================================================

    public static List<int[]> detect_artifacts(double[][] terrain_grid, double threshold) {
        List<int[]> suspicious_coords = new ArrayList<>();
        int size = terrain_grid.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i + 1 < size && Math.abs(terrain_grid[i][j] - terrain_grid[i+1][j]) > threshold) {
                    suspicious_coords.add(new int[]{i, j});
                } else if (j + 1 < size && Math.abs(terrain_grid[i][j] - terrain_grid[i][j+1]) > threshold) {
                    suspicious_coords.add(new int[]{i, j});
                }
            }
        }
        return suspicious_coords;
    }

    // =========================================================
    // UTILITY — Draw terrain in the console using ASCII
    // =========================================================

    public static void print_terrain(double[][] grid) {
        int size = grid.length;

        // 1. Find the highest and lowest points in the grid
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (grid[x][y] < min) min = grid[x][y];
                if (grid[x][y] > max) max = grid[x][y];
            }
        }

        // 2. Define our "Topographic" character map (Lowest to Highest)
        // ~ = Deep Water, . = Sand, - = Light Grass, * = Forest, # = Rocks, ^ = Snow Peak
        char[] gradient = {'~', '.', '-', '*', '#', '^'};

        // 3. Map each height to a character
        for (int y = 0; y < size; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < size; x++) {

                // Normalize the current height to a percentage (0.0 to 1.0)
                double normalized = (grid[x][y] - min) / (max - min);

                // Find the corresponding character in the gradient array
                int charIndex = (int) (normalized * (gradient.length - 1));

                // Append the character with a space so it doesn't look stretched vertically
                sb.append(gradient[charIndex]).append(" ");
            }
            System.out.println(sb.toString());
        }
    }

    // =========================================================
    // MAIN ENTRY POINT (To test the generation)
    // =========================================================

    public static void draw_terrain(int size) {

        // Higher roughness = more extreme peaks and valleys
        double roughness = 15.0;

        System.out.println("Generating " + size + "x" + size + " Terrain Map...");
        double[][] terrain = generate_terrain(size, roughness);

        System.out.println("\nASCII Topography Map:");
        System.out.println("~: Water | .: Sand | -: Grass | *: Forest | #: Rocks | ^: Snow Peaks\n");
        print_terrain(terrain);
    }
}