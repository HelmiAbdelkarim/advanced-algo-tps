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

export { split_region };