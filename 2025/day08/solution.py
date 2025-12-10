# Advent of Code 2025 - Day 08


def parse_input(raw: str):
    def split_line(line: str):
        return [int(x) for x in line.strip().split(",")]

    return [split_line(line) for line in raw.strip().splitlines() if line.strip()]


def get_euclidean_distance(p1, p2):
    return (
        pow(int(p2[0]) - int(p1[0]), 2)
        + pow(int(p2[1]) - int(p1[1]), 2)
        + pow(int(p2[2]) - int(p1[2]), 2)
    )


def part1(data) -> int:
    def find(x):
        while parent[x] != x:
            parent[x] = parent[parent[x]]
            x = parent[x]
        return x

    def union(a, b):
        ra, rb = find(a), find(b)
        if ra == rb:
            return

        if size[ra] < size[rb]:
            ra, rb = rb, ra

        parent[rb] = ra
        size[ra] += size[rb]

    distances = []
    for i in range(len(data)):
        for j in range(i + 1, len(data)):
            d = get_euclidean_distance(data[i], data[j])
            distances.append((d, i, j))
    distances.sort(key=lambda x: x[0])

    parent = list(range(len(data)))
    size = [1] * len(data)

    for _, a, b in distances[:10]:
        union(a, b)

    comp_sizes = {}
    for v in range(len(data)):
        r = find(v)
        comp_sizes[r] = comp_sizes.get(r, 0) + 1

    sizes = sorted(comp_sizes.values(), reverse=True)

    return sizes[0] * sizes[1] * sizes[2]


def part2(data) -> int:
    def find(x):
        while parent[x] != x:
            parent[x] = parent[parent[x]]
            x = parent[x]
        return x

    def union(a, b):
        ra, rb = find(a), find(b)
        if ra == rb:
            return

        if size[ra] < size[rb]:
            ra, rb = rb, ra

        parent[rb] = ra
        size[ra] += size[rb]

    distances = []
    for i in range(len(data)):
        for j in range(i + 1, len(data)):
            d = get_euclidean_distance(data[i], data[j])
            distances.append((d, i, j))
    distances.sort(key=lambda x: x[0])

    parent = list(range(len(data)))
    size = [1] * len(data)
    for _, a, b in distances:
        union(a, b)
        if size[parent[0]] == len(data):
            return data[a][0] * data[b][0]
            break

    return -1


def solve(raw: str, part: int | None = None) -> str:
    data = parse_input(raw)

    if part == 1:
        return str(part1(data))
    if part == 2:
        return str(part2(data))

    return f"{part1(data)}\n{part2(data)}"


if __name__ == "__main__":
    import sys

    cli_part = int(sys.argv[1]) if len(sys.argv) > 1 and sys.argv[1].strip() else None
    print(solve(sys.stdin.read(), cli_part))
