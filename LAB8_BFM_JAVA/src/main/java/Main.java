package main.java;

import java.util.Random;
import java.util.Arrays;

public class Main {
        public static void main(String[] args) {

            // 1. Start with 30 days of random activity
            Random random = new Random(42);
            int[] activityData = new int[30];

            // Populate with random integers between 0 and 1000
            for (int i = 0; i < 30; i++) {
                activityData[i] = random.nextInt(1001);
            }

            System.out.println("1. Original 30-day activity:");
            System.out.println(Arrays.toString(activityData) + "\n");

            // 2. Build the tree
            ActivitySegmentTree st = new ActivitySegmentTree(activityData);
            System.out.println("2. Tree built! Height: " + st.getHeight() + ", Total Nodes: " + st.getTreeSize());

            // 3. Query 7-day rolling totals for the last week (Days 23 to 29)
            System.out.println("\n3. --- 7-Day Rolling Totals (Last Week) ---");

            for (int endDay = 23; endDay < 30; endDay++) {
                int startDay = endDay - 6; // 7-day window

                int totalPosts = st.query(startDay, endDay);
                int maxPosts = st.getRangeMax(startDay, endDay);

                // Format strings to match the requested output style (e.g., 17-23)
                System.out.printf("Days %02d-%02d | Total: %4d | Peak Day Count: %3d%n",
                        startDay, endDay, totalPosts, maxPosts);
            }
        }
    }