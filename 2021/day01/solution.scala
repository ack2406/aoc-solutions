import scala.io.Source
import scala.util.Try

object Solution {
  def countWindowIncreases(input: List[Int], window: Int = 1): Int = {
    input
      .sliding(window)
      .map(_.sum)
      .toList
      .sliding(2)
      .count { case List(a, b) => a < b }
  }

  private def readInput(): List[Int] =
    Source.stdin.getLines().filter(_.nonEmpty).map(_.toInt).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = countWindowIncreases(input)
    val part2 = countWindowIncreases(input, 3)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
