import scala.annotation.tailrec
import scala.io.Source
import scala.util.Try

object Solution {
  def toDec(str: String): Int = Integer.parseInt(str, 2)

  def getGammaRate(input: List[List[String]]): String =
    input
      .transpose
      .map(_.count(_ == "1"))
      .map(x => if (x >= input.length.toFloat / 2) "1" else "0")
      .mkString("")

  def getEpsilonRate(input: List[List[String]]): String =
    getGammaRate(input)
      .split("")
      .map(x => if (x == "1") "0" else "1")
      .mkString("")

  def getPowerConsumption(input: List[List[String]]): Int =
    toDec(getGammaRate(input)) * toDec(getEpsilonRate(input))

  @tailrec
  def getRating(input: List[List[String]], n: Int, rateType: List[List[String]] => String): String = input match {
    case x if x.length == 1 => x.head.mkString
    case _ =>
      val newRates = rateType(input).split("").toList
      getRating(input.filter(x => x(x.length - n) == newRates(newRates.length - n)), n - 1, rateType)
  }

  def getLifeSupportRating(input: List[List[String]]): Int =
    toDec(getRating(input, input.head.length, getGammaRate)) * toDec(getRating(input, input.head.length, getEpsilonRate))

  private def readInput(): List[List[String]] =
    Source.stdin.getLines().filter(_.nonEmpty).map(_.trim.split("").toList).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = getPowerConsumption(input)
    val part2 = getLifeSupportRating(input)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
