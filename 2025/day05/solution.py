def parse_input(raw: str) -> tuple[list[tuple[int, int]], list[int]]:
    lines = [line.strip() for line in raw.splitlines()]
    try:
        index = lines.index("")
    except ValueError as exc:
        raise ValueError("Input must contain a blank line separator") from exc
    fresh_ranges = [parse_range(d) for d in lines[:index] if d]
    available_ids = [int(d) for d in lines[index + 1 :] if d]
    return fresh_ranges, available_ids


def parse_range(line: str) -> tuple[int, int]:
    start, end = line.split("-")
    return int(start), int(end)


def is_fresh(value: int, fresh_ranges: list[tuple[int, int]]) -> bool:
    for start, end in fresh_ranges:
        if start <= value <= end:
            return True

    return False


def part1(data: tuple[list[tuple[int, int]], list[int]]) -> int:
    result = 0

    for value in data[1]:
        if is_fresh(value, data[0]):
            result += 1

    return result


def part2(data: tuple[list[tuple[int, int]], list[int]]) -> int:
    covered_count = 0

    ranges = sorted(data[0], key=lambda item: item[0])
    if not ranges:
        return 0

    start, end = ranges[0]

    for current_start, current_end in ranges[1:]:
        if current_start <= end + 1:
            end = max(end, current_end)
        else:
            covered_count += end - start + 1
            start, end = current_start, current_end

    covered_count += end - start + 1

    return covered_count


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
