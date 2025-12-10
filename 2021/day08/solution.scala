import scala.io.Source
import scala.util.Try

object Solution {
  def countOutputValues(input: List[List[List[String]]]): Int = {
    input
      .flatMap(_.last.filter(x => List(2, 3, 4, 7).contains(x.length)))
      .length
  }

  def checkIntersect(patterns: List[List[String]], l: Int, intersected: String): Int = {
    patterns
      .head
      .find(_.length == l)
      .toList
      .head
      .intersect(intersected)
      .length
  }

  def decodeValues(patterns: List[List[String]]): List[(String, Int)] = {
    patterns.head.map(x => x.length match {
      case 2 => x -> 1
      case 3 => x -> 7
      case 4 => x -> 4
      case 7 => x -> 8
      case 5 if checkIntersect(patterns, 2, x) == 2 => x -> 3
      case 5 if checkIntersect(patterns, 4, x) == 2 => x -> 2
      case 5 => x -> 5
      case 6 if checkIntersect(patterns, 2, x) == 1 => x -> 6
      case 6 if checkIntersect(patterns, 4, x) == 4 => x -> 9
      case 6 => x -> 0
      case _ => x -> -1
    })
  }

  def getAllOutputs(input: List[List[List[String]]]): Int = {
    input
      .map(x =>
        x.last
          .map(y => decodeValues(x).find(_._1.sorted == y.sorted).map(_._2).getOrElse(0))
          .mkString
          .toInt)
      .sum
  }

  private def readInput(): List[List[List[String]]] = {
    Source.stdin.getLines().map(_.split(""" \| """).toList.map(_.split(" ").toList)).toList
  }

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = countOutputValues(input)
    val part2 = getAllOutputs(input)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
