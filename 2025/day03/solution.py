def parse_input(raw: str) -> list[str]:
    return [line.strip() for line in raw.strip().splitlines() if line.strip()]


def part1(data: list[str]) -> int:
    total = 0

    for line in data:
        first_digit = [0, -1]
        second_digit = [0, -1]

        for pos, char in enumerate(line[:-1]):
            digit = int(char)
            if digit > first_digit[0]:
                first_digit = [digit, pos]

        start_index = first_digit[1] + 1
        for offset, char in enumerate(line[start_index:]):
            digit = int(char)
            if digit > second_digit[0]:
                second_digit = [digit, offset + start_index]

        total += first_digit[0] * 10 + second_digit[0]

    return total


def part2(data: list[str]) -> int:
    total = 0

    for line in data:
        digits = [[0, -1] for _ in range(13)]

        for i in range(len(digits)):
            if i == 0:
                continue

            start_index = digits[i - 1][1] + 1
            remaining_required = len(digits) - (i + 1)
            end_index = len(line) - remaining_required

            for offset, char in enumerate(line[start_index:end_index]):
                digit = int(char)
                if digit > digits[i][0]:
                    digits[i] = [digit, offset + start_index]

        total += int("".join(str(digit[0]) for digit in digits[1:]))

    return total


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
