import scala.io.Source
import scala.util.Try

object Solution {
  def calculatePosition(input: List[List[String]]): Int = {
    input
      .foldLeft(List(0, 0)) {
        case (List(horizontalPos, depthPos), List(operator, parameter)) =>
          operator match {
            case "forward" => List(horizontalPos + parameter.toInt, depthPos)
            case "down"    => List(horizontalPos, depthPos + parameter.toInt)
            case "up"      => List(horizontalPos, depthPos - parameter.toInt)
          }
      }
      .product
  }

  def calculatePositionCorrectly(input: List[List[String]]): Int = {
    input
      .foldLeft(List(0, 0, 0)) {
        case (List(horizontalPos, depthPos, aim), List(operator, parameter)) =>
          operator match {
            case "forward" => List(horizontalPos + parameter.toInt, depthPos + (aim * parameter.toInt), aim)
            case "down"    => List(horizontalPos, depthPos, aim + parameter.toInt)
            case "up"      => List(horizontalPos, depthPos, aim - parameter.toInt)
          }
      }
      .init
      .product
  }

  private def readInput(): List[List[String]] =
    Source.stdin.getLines().filter(_.nonEmpty).map(_.split(" ").toList).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = calculatePosition(input)
    val part2 = calculatePositionCorrectly(input)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
