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

func explore(grid map[image.Point]int, current image.Point, visited map[image.Point]bool) int {
	visited[current] = true

	if grid[current] == 9 {
		return 1
	}

	score := 0

	directions := []image.Point{{0, -1}, {1, 0}, {0, 1}, {-1, 0}}
	for _, direction := range directions {
		next := current.Add(direction)

		if height, ok := grid[next]; ok && height == grid[current]+1 && !visited[next] {
			score += explore(grid, next, visited)
		}
	}

	return score
}

func exploreDistinct(grid map[image.Point]int, current image.Point, visited map[image.Point]bool) int {
	visited[current] = true

	if grid[current] == 9 {
		return 1
	}

	rating := 0

	directions := []image.Point{{0, -1}, {1, 0}, {0, 1}, {-1, 0}}
	for _, direction := range directions {
		next := current.Add(direction)

		if height, ok := grid[next]; ok && height == grid[current]+1 && !visited[next] {
			newVisited := make(map[image.Point]bool)
			for k, v := range visited {
				newVisited[k] = v
			}
			rating += exploreDistinct(grid, next, newVisited)
		}
	}

	return rating
}

func parseGrid(raw string) map[image.Point]int {
	grid := make(map[image.Point]int)
	for y, line := range strings.Split(normalize(raw), "\n") {
		for x, char := range line {
			grid[image.Point{x, y}] = int(char - '0')
		}
	}
	return grid
}

func part1(raw string) int {
	grid := parseGrid(raw)

	result := 0

	for point, value := range grid {
		if value == 0 {
			visited := make(map[image.Point]bool)
			result += explore(grid, point, visited)
		}
	}

	return result
}

func part2(raw string) int {
	grid := parseGrid(raw)

	result := 0

	for point, value := range grid {
		if value == 0 {
			visited := make(map[image.Point]bool)
			result += exploreDistinct(grid, point, visited)
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
