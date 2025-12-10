def parse_input(raw: str) -> list[str]:
    lines = raw.splitlines()
    if not lines:
        raise ValueError("Input is empty")
    return lines


def part1(lines: list[str]) -> int:
    total = 0
    grid = [line.split() for line in lines]
    for col_index in range(len(grid[0])):
        operator = grid[-1][col_index]

        running_value = int(grid[0][col_index])
        for row_index in range(1, len(grid) - 1):
            operand = int(grid[row_index][col_index])

            if operator == "*":
                running_value *= operand
            else:
                running_value += operand

        total += running_value

    return total


def part2(lines: list[str]) -> int:
    total = 0
    operators = lines[-1].split()

    operator_index = 0

    grid = lines[:-1]
    current_value = 0 if operators[operator_index] == "+" else 1
    for col_index in range(len(grid[0])):
        digit_column = ""
        for row_index in range(len(grid)):
            digit_column += grid[row_index][col_index]

        digit_column = digit_column.strip()
        if digit_column == "":
            operator_index += 1
            total += current_value
            current_value = 0 if operators[operator_index] == "+" else 1
            continue

        value = int(digit_column)
        if operators[operator_index] == "*":
            current_value *= value
        else:
            current_value += value

    total += current_value
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
