package main

import (
	"fmt"
	"io"
	"os"
	"regexp"
	"strconv"
	"strings"
)

func part1(raw string) int {
	input := raw
	mulRe := regexp.MustCompile(`mul\((\d+),(\d+)\)`)

	matches := mulRe.FindAllStringSubmatch(input, -1)

	result := 0
	for _, match := range matches {
		x, _ := strconv.Atoi(match[1])
		y, _ := strconv.Atoi(match[2])

		result += x * y
	}

	return result
}

func part2(raw string) int {
	input := raw
	mulRe := regexp.MustCompile(`mul\((\d+),(\d+)\)`)
	doRe := regexp.MustCompile(`do\(\)`)
	dontRe := regexp.MustCompile(`don't\(\)`)

	enabled := true
	result := 0
	pos1 := 0
	pos2 := 0

	for pos2 < len(input) {
		if doRe.MatchString(input[pos1:pos2]) {
			enabled = true
			pos1 = pos2
			pos2++
			continue
		}

		if dontRe.MatchString(input[pos1:pos2]) {
			enabled = false
			pos1 = pos2
			pos2++
			continue
		}

		mulMatch := mulRe.FindStringSubmatchIndex(input[pos1:pos2])
		if mulMatch != nil {
			if enabled {
				x, _ := strconv.Atoi(input[pos1+mulMatch[2] : pos1+mulMatch[3]])
				y, _ := strconv.Atoi(input[pos1+mulMatch[4] : pos1+mulMatch[5]])
				result += x * y
			}
			pos1 = pos2
			pos2++
			continue
		}

		pos2++
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
