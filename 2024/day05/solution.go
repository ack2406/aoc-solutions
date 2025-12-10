package main

import (
	"fmt"
	"io"
	"os"
	"slices"
	"strconv"
	"strings"
)

func normalize(raw string) string {
	return strings.ReplaceAll(strings.TrimSpace(raw), "\r\n", "\n")
}

func cmp(a, b string, rules []string) int {
	for _, s := range rules {
		split := strings.Split(s, "|")
		if split[0] == a && split[1] == b {
			return -1
		}
	}
	return 0
}

func calculate(raw string) (int, int) {
	sections := strings.Split(normalize(raw), "\n\n")
	if len(sections) < 2 {
		return 0, 0
	}

	rules := strings.Split(sections[0], "\n")

	part1 := 0
	part2 := 0

	for _, line := range strings.Split(sections[1], "\n") {
		if strings.TrimSpace(line) == "" {
			continue
		}

		updates := strings.Split(line, ",")
		if slices.IsSortedFunc(updates, func(a, b string) int { return cmp(a, b, rules) }) {
			slices.SortFunc(updates, func(a, b string) int { return cmp(a, b, rules) })

			n, _ := strconv.Atoi(updates[len(updates)/2])
			part1 += n
		} else {
			slices.SortFunc(updates, func(a, b string) int { return cmp(a, b, rules) })

			n, _ := strconv.Atoi(updates[len(updates)/2])
			part2 += n
		}
	}

	return part1, part2
}

func part1(raw string) int {
	result, _ := calculate(raw)
	return result
}

func part2(raw string) int {
	_, result := calculate(raw)
	return result
}

func parsePart(args []string) int {
	if len(args) > 1 {
		if p, err := strconv.Atoi(strings.TrimSpace(args[1])); err == nil {
			return p
		}
	}
	return 0
}

func main() {
	data, _ := io.ReadAll(os.Stdin)
	input := string(data)
	part := parsePart(os.Args)

	switch part {
	case 1:
		fmt.Println(part1(input))
	case 2:
		fmt.Println(part2(input))
	default:
		p1, p2 := calculate(input)
		fmt.Printf("%d\n%d\n", p1, p2)
	}
}
