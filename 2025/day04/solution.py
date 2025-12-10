def parse_input(raw: str) -> list[list[str]]:
    return [list(line.rstrip("\n")) for line in raw.strip().splitlines() if line]


def count_neighbors(grid: list[list[str]], row: int, col: int) -> int:
    height = len(grid)
    width = len(grid[0])
    count = 0

    for dr in (-1, 0, 1):
        for dc in (-1, 0, 1):
            if dr == 0 and dc == 0:
                continue
            nr = row + dr
            nc = col + dc
            if 0 <= nr < height and 0 <= nc < width and grid[nr][nc] == "@":
                count += 1

    return count


def part1(data: list[list[str]]) -> int:
    marked = 0
    next_grid = [row[:] for row in data]

    height = len(data)
    width = len(data[0])

    for i in range(height):
        for j in range(width):
            if count_neighbors(data, i, j) < 4 and data[i][j] == "@":
                marked += 1
                next_grid[i][j] = "x"

    return marked


def part2(data: list[list[str]]) -> int:
    marked_total = 0
    next_grid = [row[:] for row in data]

    height = len(data)
    width = len(data[0])

    marked_this_round = -1

    while marked_this_round != 0:
        marked_this_round = 0
        for i in range(height):
            for j in range(width):
                if count_neighbors(data, i, j) < 4 and data[i][j] == "@":
                    marked_this_round += 1
                    next_grid[i][j] = "x"

        marked_total += marked_this_round
        data = [row[:] for row in next_grid]

    return marked_total


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
