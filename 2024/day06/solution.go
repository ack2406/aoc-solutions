package main

import (
	"fmt"
	"image"
	"io"
	"os"
	"strconv"
	"strings"
)

func normalize(raw string) string {
	return strings.ReplaceAll(strings.TrimSpace(raw), "\r\n", "\n")
}

func patrol(grid map[image.Point]rune, start image.Point, obstacle image.Point) int {
	delta := []image.Point{{0, -1}, {1, 0}, {0, 1}, {-1, 0}}
	p := start
	d := 0
	seen := map[image.Point]int{}

	for {
		if _, inBoundaries := grid[p]; !inBoundaries {
			return len(seen)
		} else if 1<<d&seen[p] != 0 {
			return -1
		}

		seen[p] |= 1 << d

		if n := p.Add(delta[d]); grid[n] == '#' || n == obstacle {
			d = (d + 1) % len(delta)
		} else {
			p = n
		}
	}
}

func parseGrid(raw string) (map[image.Point]rune, image.Point) {
	lines := strings.Split(normalize(raw), "\n")

	grid := map[image.Point]rune{}
	start := image.Point{}
	for i, row := range lines {
		for j, col := range row {
			if col == '^' {
				start = image.Point{j, i}
			}
			grid[image.Point{j, i}] = col
		}
	}

	return grid, start
}

func part1(raw string) int {
	grid, start := parseGrid(raw)
	return patrol(grid, start, image.Point{-1, -1})
}

func part2(raw string) int {
	grid, start := parseGrid(raw)

	result := 0
	for p := range grid {
		if patrol(grid, start, p) == -1 {
			result++
		}
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
