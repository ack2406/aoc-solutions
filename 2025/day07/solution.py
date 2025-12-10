from collections import defaultdict


def parse_input(raw: str) -> list[str]:
    return raw.strip().splitlines()


def part1(data: list[str]) -> int:
    result = 0

    beams = {data[0].index("S")}
    for i in range(1, len(data)):
        new_beams = set()

        for beam in beams:
            if data[i][beam] == "^":
                new_beams.add(beam - 1)
                new_beams.add(beam + 1)
                result += 1
            else:
                new_beams.add(beam)
        beams = new_beams

    return result


def part2(data: list[str]) -> int:
    beams = {data[0].index("S"): 1}
    for i in range(1, len(data)):
        new_beams = defaultdict(int)

        for beam_key, beam_value in beams.items():
            if data[i][beam_key] == "^":
                new_beams[beam_key - 1] += beam_value
                new_beams[beam_key + 1] += beam_value
            else:
                new_beams[beam_key] += beam_value

        beams = new_beams

    return sum(beams.values())


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
