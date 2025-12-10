import scala.annotation.tailrec
import scala.io.Source
import scala.util.Try

object Solution {
  private def increaseEnergy(octs: List[List[(Int, Boolean)]], i: Int, j: Int): (List[List[(Int, Boolean)]], Int) = {
    var flashes = 0
    var newOcts = octs
    val newEnergy: (Int, Boolean) = newOcts(i)(j) match {
      case (9, false) =>
        flashes += 1
        val positions = for {
          posx <- i - 1 to i + 1
          posy <- j - 1 to j + 1
          if !(posx == i && posy == j) &&
            posx >= 0 && posx < newOcts.length &&
            posy >= 0 && posy < newOcts.head.length
        } yield (posx, posy)
        newOcts = newOcts.updated(i, newOcts(i).updated(j, (10, true)))
        positions.foreach { case (x, y) =>
          val (gridAfter, extraFlashes) = increaseEnergy(newOcts, x, y)
          newOcts = gridAfter
          flashes += extraFlashes
        }
        (10, true)
      case (energy, flashed) =>
        (energy + 1, flashed)
    }
    newOcts = newOcts.updated(i, newOcts(i).updated(j, newEnergy))
    (newOcts, flashes)
  }

  private def makeStep(octs: List[List[(Int, Boolean)]]): (List[List[(Int, Boolean)]], Int) = {
    var newOcts = octs
    var flashes = 0

    for (i <- octs.indices; j <- octs.head.indices) {
      val (gridAfter, extraFlashes) = increaseEnergy(newOcts, i, j)
      newOcts = gridAfter
      flashes += extraFlashes
    }

    val reset = newOcts.map(row => row.map {
      case (_, true) => (0, false)
      case x         => x
    })

    (reset, flashes)
  }

  @tailrec
  private def simulate(octs: List[List[(Int, Boolean)]], steps: Int, flashes: Int = 0): (List[List[(Int, Boolean)]], Int) = {
    steps match {
      case 0 => (octs, flashes)
      case _ =>
        val (next, f) = makeStep(octs)
        simulate(next, steps - 1, flashes + f)
    }
  }

  @tailrec
  private def findSync(octs: List[List[(Int, Boolean)]], counter: Int = 0): Int = {
    if (octs.forall(_.forall { case (energy, _) => energy == 0 })) counter
    else {
      val (next, _) = makeStep(octs)
      findSync(next, counter + 1)
    }
  }

  private def readInput(): List[List[(Int, Boolean)]] =
    Source.stdin.getLines().filter(_.nonEmpty).map(row => row.trim.split("").map(el => (el.toInt, false)).toList).toList

  def main(args: Array[String]): Unit = {
    val input = readInput()
    val part = args.headOption.flatMap(arg => Try(arg.trim.toInt).toOption)

    val (_, part1Flashes) = simulate(input, 100)
    val part2 = findSync(input)

    part match {
      case Some(1) => println(part1Flashes)
      case Some(2) => println(part2)
      case _ =>
        println(part1Flashes)
        println(part2)
    }
  }
}
