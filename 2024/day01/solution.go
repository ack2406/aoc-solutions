package main

import (
	"fmt"
	"io"
	"os"
	"slices"
	"strconv"
	"strings"
)

func parseInput(raw string) ([]int, []int) {
	var list1, list2 []int

	for _, line := range strings.Split(strings.TrimSpace(raw), "\n") {
		fields := strings.Fields(line)
		if len(fields) < 2 {
			continue
		}

		n1, _ := strconv.Atoi(fields[0])
		n2, _ := strconv.Atoi(fields[1])

		list1 = append(list1, n1)
		list2 = append(list2, n2)
	}

	return list1, list2
}

func part1(raw string) int {
	list1, list2 := parseInput(raw)

	slices.Sort(list1)
	slices.Sort(list2)

	result := 0
	for i := range list1 {
		diff := list1[i] - list2[i]
		if diff < 0 {
			diff = -diff
		}
		result += diff
	}

	return result
}

func part2(raw string) int {
	list1, list2 := parseInput(raw)
	counts := map[int]int{}

	for _, n := range list2 {
		counts[n]++
	}

	result := 0
	for _, n := range list1 {
		result += n * counts[n]
	}

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
		fmt.Printf("%d\n%d\n", part1(input), part2(input))
	}
}
