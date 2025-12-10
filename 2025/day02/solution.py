def parse_input(raw: str) -> list[tuple[int, int]]:
    lines = [line.strip() for line in raw.strip().splitlines() if line.strip()]
    if not lines:
        return []
    segments = lines[0].split(",")
    return [
        (int(start), int(end))
        for start, end in (segment.split("-") for segment in segments)
    ]


def part1(data: list[tuple[int, int]]) -> int:
    total = 0

    for start, end in data:
        for candidate in range(start, end + 1):
            candidate_str = str(candidate)
            candidate_len = len(candidate_str)

            if candidate_len % 2 == 0:
                midpoint = candidate_len // 2
                if candidate_str[:midpoint] == candidate_str[midpoint:]:
                    total += candidate

    return total


def part2(data: list[tuple[int, int]]) -> int:
    periodic_ids = set()
    for start, end in data:
        for candidate in range(start, end + 1):
            candidate_str = str(candidate)
            candidate_len = len(candidate_str)

            for chunk_len in range(1, (candidate_len // 2) + 1):
                if candidate_len % chunk_len == 0:
                    pattern = candidate_str[:chunk_len] * (candidate_len // chunk_len)
                    if pattern == candidate_str:
                        periodic_ids.add(candidate)

    return sum(periodic_ids)


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
