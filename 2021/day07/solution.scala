import scala.io.Source
import scala.util.Try

object Solution {
  def getOptimalPosition(crabs: List[Int]): Int = {
    (crabs.min to crabs.max)
      .map(pos => crabs.map(crab => (crab - pos).abs).sum)
      .min
  }

  def getCorrectPosition(crabs: List[Int]): Int = {
    (crabs.min to crabs.max)
      .map(pos =>
        crabs.map(crab => (crab - pos).abs * ((crab - pos).abs + 1) / 2).sum)
      .min
  }

  private def readInput(): List[Int] =
    Source.stdin.getLines().flatMap(_.split(",").filter(_.nonEmpty).map(_.toInt)).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = getOptimalPosition(input)
    val part2 = getCorrectPosition(input)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
