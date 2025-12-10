package main

import (
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func isSafe(report []int) bool {
	flag := 11

	for i := 0; i < len(report)-1; i++ {
		diff := report[i+1] - report[i]

		if diff < 1 || diff > 3 {
			flag &= 01
		}

		if diff < -3 || diff > -1 {
			flag &= 10
		}
	}

	return flag != 0
}

func isSafeWithDampener(report []int) bool {
	if isSafe(report) {
		return true
	}

	for i := 0; i < len(report); i++ {
		newReport := make([]int, len(report)-1)

		copy(newReport[:i], report[:i])
		copy(newReport[i:], report[i+1:])

		if isSafe(newReport) {
			return true
		}
	}

	return false
}

func getReports(input string) [][]int {
	var reports [][]int

	for _, line := range strings.Split(strings.TrimSpace(input), "\n") {
		fields := strings.Fields(line)
		report := make([]int, len(fields))

		for j, field := range fields {
			report[j], _ = strconv.Atoi(field)
		}

		reports = append(reports, report)
	}

	return reports
}

func part1(raw string) int {
	reports := getReports(raw)
	count := 0

	for _, report := range reports {
		if isSafe(report) {
			count++
		}
	}

	return count
}

func part2(raw string) int {
	reports := getReports(raw)
	count := 0

	for _, report := range reports {
		if isSafeWithDampener(report) {
			count++
		}
	}

	return count
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
