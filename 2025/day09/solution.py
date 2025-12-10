from collections import deque


def parse_input(raw: str):
    return [
        [int(x) for x in line.strip().split(",")]
        for line in raw.strip().splitlines()
        if line.strip()
    ]


def part1(data) -> int:
    n = len(data)

    best = 0
    for i in range(n):
        ax, ay = data[i]
        for j in range(i + 1, n):
            bx, by = data[j]
            w = abs(ax - bx) + 1
            h = abs(ay - by) + 1
            area = w * h
            if area > best:
                best = area

    return best


def part2(data) -> int:
    n = len(data)

    xs = sorted({x for x, y in data})
    ys = sorted({y for x, y in data})

    x_index = {x: i for i, x in enumerate(xs)}
    y_index = {y: i for i, y in enumerate(ys)}

    W = len(xs) * 2 + 3
    H = len(ys) * 2 + 3

    view = [["." for _ in range(W)] for _ in range(H)]

    def compress_point(x: int, y: int) -> tuple[int, int]:
        return x_index[x] * 2 + 1, y_index[y] * 2 + 1

    compressed_data: list[tuple[int, int]] = []
    for x, y in data:
        cx, cy = compress_point(x, y)
        compressed_data.append((cx, cy))
        view[cy][cx] = "#"

    for i in range(n):
        ax, ay = compressed_data[i - 1]
        bx, by = compressed_data[i]

        if ax != bx:
            if ax > bx:
                ax, bx = bx, ax
            for x in range(ax + 1, bx):
                view[ay][x] = "X"
        else:
            if ay > by:
                ay, by = by, ay
            for y in range(ay + 1, by):
                if view[y][ax] == ".":
                    view[y][ax] = "X"

    que = deque([(0, 0)])

    while que:
        y, x = que.popleft()
        if not (0 <= y < H and 0 <= x < W):
            continue
        if view[y][x] != ".":
            continue

        view[y][x] = "O"

        for dy, dx in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            ny, nx = y + dy, x + dx
            if 0 <= ny < H and 0 <= nx < W and view[ny][nx] == ".":
                que.append((ny, nx))

    for y in range(H):
        for x in range(W):
            if view[y][x] == ".":
                view[y][x] = "X"
            elif view[y][x] == "O":
                view[y][x] = "."

    bad = [[0] * (W + 1) for _ in range(H + 1)]

    for y in range(H):
        row_bad = bad[y + 1]
        prev_row_bad = bad[y]
        for x in range(W):
            is_bad = view[y][x] == "." and x % 2 == 1 and y % 2 == 1
            row_bad[x + 1] = (
                row_bad[x]
                + prev_row_bad[x + 1]
                - prev_row_bad[x]
                + (1 if is_bad else 0)
            )

    best = 0
    for i in range(n):
        ax, ay = data[i]
        cax, cay = compressed_data[i]
        for j in range(i + 1, n):
            bx, by = data[j]

            x1, x2 = (ax, bx) if ax <= bx else (bx, ax)
            y1, y2 = (ay, by) if ay <= by else (by, ay)

            w = x2 - x1 + 1
            h = y2 - y1 + 1
            area = w * h

            if area <= best:
                continue

            cbx, cby = compressed_data[j]
            cx1, cx2 = (cax, cbx) if cax <= cbx else (cbx, cax)
            cy1, cy2 = (cay, cby) if cay <= cby else (cby, cay)

            bad_inside = (
                bad[cy2 + 1][cx2 + 1]
                - bad[cy1][cx2 + 1]
                - bad[cy2 + 1][cx1]
                + bad[cy1][cx1]
            )

            if bad_inside > 0:
                continue

            best = area

    return best


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
