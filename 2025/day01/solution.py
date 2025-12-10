def parse_input(raw: str) -> list[tuple[int, int]]:
    lines = [line.strip() for line in raw.strip().splitlines() if line.strip()]

    def direction_delta(char: str) -> int:
        return 1 if char == "R" else -1

    return [(direction_delta(line[0]), int(line[1:])) for line in lines]


def part1(data: list[tuple[int, int]]) -> int:
    crossings = 0
    position = 50

    for direction, value in data:
        position = (position + (direction * value)) % 100
        if position == 0:
            crossings += 1

    return crossings


def part2(data: list[tuple[int, int]]) -> int:
    crossings = 0
    position = 50

    for direction, value in data:
        for _ in range(value):
            position = (position + direction) % 100
            if position == 0:
                crossings += 1

    return crossings


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
