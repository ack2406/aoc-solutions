package main

import (
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func countXmases(word string) int {
	counter := 0
	if word == "XMAS" {
		counter++
	}
	if word == "SAMX" {
		counter++
	}
	return counter
}

func part1(raw string) int {
	var wordSearch []string
	wordSearch = append(wordSearch, strings.Fields(raw)...)

	result := 0
	for i, line := range wordSearch {
		for j := 0; j < len(line); j++ {
			if j < len(line)-3 {
				result += countXmases(line[j : j+4])
			}

			if i < len(wordSearch)-3 {
				word1, word2, word3 := "", "", ""

				for k := 0; k < 4; k++ {
					if j < len(line)-3 {
						word1 += string(wordSearch[i+k][j+k])
					}
					if j > 2 {
						word2 += string(wordSearch[i+k][j-k])
					}
					word3 += string(wordSearch[i+k][j])
				}

				result += countXmases(word1)
				result += countXmases(word2)
				result += countXmases(word3)
			}
		}
	}

	return result
}

func part2(raw string) int {
	var wordSearch []string
	wordSearch = append(wordSearch, strings.Fields(raw)...)

	result := 0
	for i := 1; i < len(wordSearch)-1; i++ {
		for j := 1; j < len(wordSearch[i])-1; j++ {
			if wordSearch[i][j] == 'A' {
				nw := wordSearch[i+1][j-1]
				ne := wordSearch[i+1][j+1]
				sw := wordSearch[i-1][j-1]
				se := wordSearch[i-1][j+1]

				if (nw == 'M' && se == 'S' || nw == 'S' && se == 'M') &&
					(ne == 'M' && sw == 'S' || ne == 'S' && sw == 'M') {
					result++
				}
			}
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
