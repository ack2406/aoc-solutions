import scala.io.Source
import scala.util.Try

object Solution {
  def getLines(input: List[String]): List[List[List[Int]]] = {
    input
      .filter(_.nonEmpty)
      .map(_.split(" -> ").map(_.split(",").map(_.toInt).toList).toList)
  }

  def getPointsFromLine(line: List[List[Int]]): List[List[List[Int]]] = {
    line.transpose.map {
      case List(a, b) if b > a  => (a to b).toList
      case List(a, b)           => (b to a).toList.reverse
    } match {
      case List(points, List(n)) => List(points.map(x => List(n, x)))
      case List(List(n), points) => List(points.map(x => List(x, n)))
      case List(points1, points2) => List(points2.lazyZip(points1).map(List(_, _)))
    }
  }

  def getLinesOfVents(lines: List[List[List[Int]]]): List[List[Int]] =
    lines.flatMap(getPointsFromLine).flatten

  def getDiagramSize(lines: List[List[Int]]): (Int, Int) = {
    lines.foldLeft((0, 0)) {
      case ((acc1, acc2), List(a, b)) => (List(acc1, a).max, List(acc2, b).max)
    }
  }

  def tabulaRasa(lines: List[List[Int]]): List[List[Int]] = {
    List.fill(getDiagramSize(lines)._1 + 1)(List.fill(getDiagramSize(lines)._2 + 1)(0))
  }

  def createDiagram(lines: List[List[Int]]): List[List[Int]] = {
    lines.foldLeft(tabulaRasa(lines)) {
      case (acc, List(a, b)) => acc.updated(a, acc(a).updated(b, acc(a)(b) + 1))
    }
  }

  def countOverlappingPoints(lines: List[List[List[Int]]]): Int = {
    createDiagram(getLinesOfVents(lines)).flatten.count(_ > 1)
  }

  private def readInput(): List[String] = Source.stdin.getLines().toList

  def main(args: Array[String]): Unit = {
    val input = getLines(readInput())
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val straightLines = input.filter { case List(List(x1, y1), List(x2, y2)) => x1 == x2 || y1 == y2 }
    val part1 = countOverlappingPoints(straightLines)
    val part2 = countOverlappingPoints(input)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
