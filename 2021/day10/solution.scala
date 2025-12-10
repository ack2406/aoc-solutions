import scala.collection.mutable.Stack
import scala.io.Source
import scala.util.Try

object Solution {
  def getOpening(symbol: String): String = symbol match {
    case ")" => "("
    case "]" => "["
    case "}" => "{"
    case ">" => "<"
  }

  def getClosure(symbol: String): String = symbol match {
    case "(" => ")"
    case "[" => "]"
    case "{" => "}"
    case "<" => ">"
  }

  def getScore(symbol: String): Long = symbol match {
    case ")" => 3
    case "]" => 57
    case "}" => 1197
    case ">" => 25137
  }

  def getAutocompleteScore(symbol: String): Long = symbol match {
    case ")" => 1
    case "]" => 2
    case "}" => 3
    case ">" => 4
  }

  def checkCorruption(line: List[String]): String = {
    val stack = Stack[String]()
    for (symbol <- line) {
      if (List("(", "{", "[", "<").contains(symbol)) stack.push(symbol)
      else if (stack.pop != getOpening(symbol)) return symbol
    }
    ""
  }

  def corruptionDiscard(line: List[String]): Boolean = {
    val stack = Stack[String]()
    for (symbol <- line) {
      if (List("(", "{", "[", "<").contains(symbol)) stack.push(symbol)
      else if (stack.pop != getOpening(symbol)) return false
    }
    true
  }

  def getCorruptedSum(input: List[List[String]]): Long = {
    input
      .map(checkCorruption)
      .filter(_.nonEmpty)
      .map(getScore)
      .sum
  }

  def completeOneLine(line: List[String]): List[String] = {
    val stack = Stack[String]()
    for (symbol <- line) {
      if (List("(", "{", "[", "<").contains(symbol)) stack.push(symbol)
      else stack.pop()
    }
    stack.toList
  }

  def autocompleteLines(input: List[List[String]]): List[Long] = {
    input
      .filter(corruptionDiscard)
      .map(line => completeOneLine(line).map(getClosure))
      .map(line => line.foldLeft(0: Long)((acc, symbol) => (acc * 5) + getAutocompleteScore(symbol)))
      .sorted
  }

  def getMiddleScore(scores: List[Long]): Long = {
    scores(scores.length / 2)
  }

  private def readInput(): List[List[String]] = Source.stdin.getLines().map(_.split("").toList).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = getCorruptedSum(input)
    val part2 = getMiddleScore(autocompleteLines(input))

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
