import { split_region, count_points_in_region, find_dense_regions } from "./exercice1.js";

const points = [
    { x: 1, y: 2 },
    { x: 3, y: 4 },
    { x: 5, y: 6 },
    { x: 7, y: 8 },
    { x: 9, y: 10 },
];

console.log("split_region(0, 0, 10, 10, 2):", split_region(0, 0, 10, 10, 2));
console.log("count_points_in_region(points, 0, 0, 10, 10):", count_points_in_region(points, 0, 0, 10, 10));
console.log("find_dense_regions(points, 0, 0, 10, 10, 2, 0.1):", find_dense_regions(points, 0, 0, 10, 10, 2, 0.1));