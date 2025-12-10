package main

import (
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
)

func createBlocks(input string) []int {
	input = strings.TrimSpace(input)
	blocks := []int{}

	flag := true
	id := 0

	for _, word := range input {
		n := int(word - '0')
		for i := 0; i < n; i++ {
			if flag {
				blocks = append(blocks, id)
			} else {
				blocks = append(blocks, -1)
			}
		}

		if flag {
			id++
		}

		flag = !flag
	}

	return blocks
}

func part1(raw string) int {
	blocks := createBlocks(raw)

	l, r := 0, len(blocks)-1

out:
	for l < r {
		if blocks[r] == -1 {
			r--
		} else {
			for blocks[l] != -1 {
				l++

				if !(l < r) {
					break out
				}
			}
			blocks[l], blocks[r] = blocks[r], blocks[l]
		}
	}

	result := 0
	for pos, blockId := range blocks {
		if blockId == -1 {
			break
		}

		result += blockId * pos
	}

	return result
}

func part2(raw string) int {
	blocks := createBlocks(raw)

	maxFileID := 0

	for i := len(blocks) - 1; i >= 0; i-- {
		if blocks[i] != -1 {
			maxFileID = blocks[i]
			break
		}
	}

	for fileId := maxFileID; fileId > 0; fileId-- {
		l1, r1 := -1, -1
		for i := len(blocks) - 1; i >= 0; i-- {
			if blocks[i] == fileId {
				if r1 == -1 {
					r1 = i
				}

				if r1 != -1 && blocks[i] != fileId {
					break
				}

				l1 = i
			}
		}

		fileSize := r1 - l1 + 1

		l2, size := -1, 0

		for i := 0; i < l1; i++ {
			if blocks[i] == -1 {
				if l2 == -1 {
					l2 = i
				}
				size++
				if size >= fileSize {
					break
				}
			} else {
				l2 = -1
				size = 0
			}
		}

		if size < fileSize {
			continue
		}

		for i := l1; i <= r1; i++ {
			blocks[i] = -1
		}

		for i := 0; i < fileSize; i++ {
			blocks[l2+i] = fileId
		}
	}

	result := 0
	for pos, blockId := range blocks {
		if blockId == -1 {
			continue
		}

		result += blockId * pos
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
