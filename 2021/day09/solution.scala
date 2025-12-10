import scala.io.Source
import scala.util.Try

object Solution {
  def maxPoint(input: List[List[Int]]): Int = input.flatten.max

  def getLowPointsInLine(input: List[List[Int]]): List[List[List[Int]]] = {
    input
      .map(x => List(List(maxPoint(input), x.head, x.tail.head)) ++ x.sliding(3).toList ++ List(List(x.init.last, x.last, maxPoint(input))))
  }

  def getPoints(input: List[List[Int]]): List[List[Int]] = {
    getLowPointsInLine(input)
      .zip(getLowPointsInLine(input.transpose).transpose)
      .map(x =>
        x._1.zip(x._2).map {
          case (List(a, b, c), List(d, _, f)) if List(a, b, c, d, f).min == b && List(a, b, c, d, f).count(_ == b) == 1 => b + 1
          case _                                                                                                                         => -1
        })
  }

  def getSumPoints(input: List[List[Int]]): Int = {
    getPoints(input).map(_.filter(_ != -1)).flatten.sum
  }

  def getBasinCoordinates(input: List[List[Int]]): List[(Int, Int)] = {
    getPoints(input)
      .map(_.zipWithIndex)
      .zipWithIndex
      .map(x => (x._1.filter(_._1 != -1), x._2))
      .filter(_._1.nonEmpty)
      .map(x => x._1.map(y => (x._2, y._2)))
      .flatten
  }

  def getBasinMap(points: List[List[Int]], pt: (Int, Int)): List[List[Int]] = {
    var newPoints = points.updated(pt._1, points(pt._1).updated(pt._2, -1))

    if (pt._1 > 0 && points(pt._1 - 1)(pt._2) != -1 && points(pt._1 - 1)(pt._2) != maxPoint(points)) {
      newPoints = getBasinMap(newPoints, (pt._1 - 1, pt._2))
    }
    if (pt._1 < points.length - 1 && points(pt._1 + 1)(pt._2) != -1 && points(pt._1 + 1)(pt._2) != maxPoint(points)) {
      newPoints = getBasinMap(newPoints, (pt._1 + 1, pt._2))
    }
    if (pt._2 > 0 && points(pt._1)(pt._2 - 1) != -1 && points(pt._1)(pt._2 - 1) != maxPoint(points)) {
      newPoints = getBasinMap(newPoints, (pt._1, pt._2 - 1))
    }
    if (pt._2 < points.head.length - 1 && points(pt._1)(pt._2 + 1) != -1 && points(pt._1)(pt._2 + 1) != maxPoint(points)) {
      newPoints = getBasinMap(newPoints, (pt._1, pt._2 + 1))
    }
    newPoints
  }

  def getBasinSizes(input: List[List[Int]]): Int = {
    getBasinCoordinates(input)
      .map(x => getBasinMap(input, x).flatten.count(_ == -1))
      .sorted
      .reverse
      .take(3)
      .product
  }

  private def readInput(): List[List[Int]] =
    Source.stdin.getLines().filter(_.nonEmpty).map(_.split("").toList.map(_.toInt)).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = getSumPoints(input)
    val part2 = getBasinSizes(input)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
