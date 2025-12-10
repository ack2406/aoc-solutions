import scala.annotation.tailrec
import scala.io.Source
import scala.util.Try

object Solution {
  @tailrec
  def doCycle(fishes: List[Int], days: Int): Long = days match {
    case 0 => fishes.length
    case n =>
      doCycle(
        fishes
          .map(timer => if (timer == 0) 6 else timer - 1) ++
          fishes
            .map(_ - 1)
            .filter(_ == -1)
            .map(_ => 8),
        n - 1
      )
  }

  def createTimers(fishes: List[Int]): List[Long] = {
    fishes.foldLeft(List.fill(9)(0): List[Long])((acc, t) => acc.updated(t, acc(t) + 1))
  }

  def fastCycle(timers: List[Long], days: Int): Long = {
    if (days == 0) timers.sum
    else
      timers match {
        case List(t0, t1, t2, t3, t4, t5, t6, t7, t8) =>
          fastCycle(List(t1, t2, t3, t4, t5, t6, t7 + t0, t8, t0), days - 1)
      }
  }

  private def readInput(): List[Int] =
    Source.stdin.getLines().flatMap(_.split(",").filter(_.nonEmpty).map(_.toInt)).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val part1 = doCycle(input, 80)
    val part2 = fastCycle(createTimers(input), 256)

    part match {
      case Some(1) => println(part1)
      case Some(2) => println(part2)
      case _ =>
        println(part1)
        println(part2)
    }
  }
}
