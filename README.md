# aoc-solutions

Advent of Code solutions organized by year/day, runnable via the companion CLI runner.

## Related repo
- Runner lives in `ack2406/aoc-runner`: https://github.com/ack2406/aoc-runner

## Layout
- Each day: `<year>/dayXX/`
- Files:
  - `meta.json` (language, entry point, year/day)
  - `sample.txt` (local sample input)
  - `solution.*` (language-specific entry point; see below)

### Language mapping (current content)
- 2021: Scala (`solution.scala`, days 01-11)
- 2024: Go (`solution.go`, days 01-10)
- 2025: Python (`solution.py`, days 01-10)

Example `meta.json`:
```json
{
  "Year": 2024,
  "Day": 1,
  "Language": "go",
  "EntryPoint": "solution.go"
}
```

## Running with the runner
1) Ensure `ack2406/aoc-runner` sits next to this repo (default `SolutionPath` is `../aoc-solutions`).
2) From `aoc-runner`:
```
dotnet run --project AocRunner -- run 2024 1
dotnet run --project AocRunner -- run 2024 1 2 --sample
```
3) Provide language runtimes as needed:
   - Python: `python3`
   - Go: `go`
   - Scala: `scala-cli` (preferred) or `scala`

## Running directly (without the runner)
- Python (2025): `cat sample.txt | python3 solution.py [part]`
- Go (2024): `cat sample.txt | go run solution.go [part]`
- Scala (2021): `cat sample.txt | scala-cli run solution.scala [part]`

## Adding new days
- Use the runner to scaffold: `dotnet run --project AocRunner -- new <year> <day> --lang <python|go|scala>`
- Or follow the layout above and create `meta.json`, `sample.txt`, and the entrypoint manually.
