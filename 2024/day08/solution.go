package main

import (
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func part1(_ string) string {
	return "TODO"
}

func part2(_ string) string {
	return "TODO"
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
		fmt.Printf("%s\n%s\n", part1(input), part2(input))
	}
}
