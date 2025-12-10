import scala.annotation.tailrec
import scala.io.Source
import scala.util.Try

object Solution {

  def getNumbersOrder(input: List[String]): List[Int] = {
    input
      .headOption
      .getOrElse("")
      .split(",")
      .filter(_.nonEmpty)
      .map(_.toInt)
      .toList
  }

  def getBoards(input: List[String]): List[List[List[(Int, Boolean)]]] = {
    input
      .drop(1)
      .filter(_.nonEmpty)
      .map(_.trim.split(" ").toList.filter(_.nonEmpty).map(x => (x.toInt, false)))
      .grouped(5)
      .toList
  }

  def checkBoard(board: List[List[(Int, Boolean)]]): Boolean = {
    board.map(_.count({ case (_, x) => x })).exists(_ == 5)
  }

  def checkIfWinner(board: List[List[(Int, Boolean)]]): Boolean = {
    checkBoard(board) || checkBoard(board.transpose)
  }

  def markNumbers(board: List[List[(Int, Boolean)]], number: Int): List[List[(Int, Boolean)]] = {
    board
      .map(_.map({
        case (boardNumber, mark) => if (boardNumber == number) (boardNumber, true) else (boardNumber, mark)
      }))
  }

  def getIndexOfLastToWinBoard(boardState: List[Boolean], lastBoardState: List[Boolean]): Int = {
    boardState.zipAll(lastBoardState, false, false).indexWhere({ case (a, b) => a != b })
  }

  def calculateScore(board: List[List[(Int, Boolean)]], number: Int): Int = {
    board.flatMap(x => x.filter({ case (_, mark) => !mark })).map({ case (n, _) => n }).sum * number
  }

  @tailrec
  def playBingo(boards: List[List[List[(Int, Boolean)]]], numbers: List[Int], lastCall: Int = 0): Int = {
    if (boards.exists(checkIfWinner)) {
      calculateScore(boards(boards.indexWhere(checkIfWinner)), lastCall)
    } else if (numbers.nonEmpty) {
      playBingo(boards.map(x => markNumbers(x, numbers.head)), numbers.tail, numbers.head)
    } else 0
  }

  @tailrec
  def playSquidBingo(
      boards: List[List[List[(Int, Boolean)]]],
      numbers: List[Int],
      lastCall: Int = 0,
      lastBoardState: List[Boolean] = List()): Int = {
    val winners = boards.map(checkIfWinner)
    if (winners.count(identity) == boards.length) {
      calculateScore(boards(getIndexOfLastToWinBoard(winners, lastBoardState)), lastCall)
    } else if (numbers.nonEmpty) {
      playSquidBingo(boards.map(x => markNumbers(x, numbers.head)), numbers.tail, numbers.head, winners)
    } else 0
  }

  private def readInput(): List[String] = Source.stdin.getLines().toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = playBingo(getBoards(input), getNumbersOrder(input))
    val part2 = playSquidBingo(getBoards(input), getNumbersOrder(input))

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
