function split_region(x, y, width, height, min_size, regions = []) {
    if (width < min_size || height < min_size) {
        return regions;
    }

    const half_width = width / 2;
    const half_height = height / 2;

    if (half_width < min_size || half_height < min_size) {
        regions.push({ x, y, width, height });
        return regions;
    }

    split_region(x, y, half_width, half_height, min_size, regions);
    split_region(x + half_width, y, half_width, half_height, min_size, regions);
    split_region(x, y + half_height, half_width, half_height, min_size, regions);
    split_region(x + half_width, y + half_height, half_width, half_height, min_size, regions);

    return regions;
}

function count_points_in_region(points, x, y, width, height) {
    let count = 0;

    for (const point of points) {
        if (point.x >= x && point.x < x + width &&
            point.y >= y && point.y < y + height) {
            count++;
        }
    }

    return count;
}

function find_dense_regions(points, x, y, width, height, min_size, density_threshold) {
    const point_count = count_points_in_region(points, x, y, width, height);
    const density = point_count / (width * height);

    const half_width  = width / 2;
    const half_height = height / 2;

    if (half_width < min_size || half_height < min_size) {
        return density > density_threshold ? [{ x, y, width, height }] : [];
    }

    const q1 = find_dense_regions(points, x, y, half_width, half_height, min_size, density_threshold);
    const q2 = find_dense_regions(points, x + half_width, y, half_width, half_height, min_size, density_threshold);
    const q3 = find_dense_regions(points, x, y + half_height, half_width, half_height, min_size, density_threshold);
    const q4 = find_dense_regions(points, x + half_width, y + half_height, half_width, half_height, min_size, density_threshold);

    return q1.concat(q2, q3, q4);
}

export { split_region, count_points_in_region, find_dense_regions };