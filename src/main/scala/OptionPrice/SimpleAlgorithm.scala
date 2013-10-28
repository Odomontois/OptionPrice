package OptionPrice

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 10/28/13
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */

trait SimpleAlgorithm extends Algorithm {
  this: Calculation =>

  val coef = Math.exp(-config.r * config.deltaT)

  def calcBranch(points: Seq[Point], factor: Double = 1): Double = points match {
    case Nil           => (config.X - factor * config.S) max 0
    case point :: rest => coef * {
      import point._
      pu * u * calcBranch(rest, factor * u) + pd * d * calcBranch(rest, factor * d) +
        (if (pm > 0) pm * calcBranch(rest, factor) else 0.0)
    }
  }

  override def calc: Double = calcBranch(points)
}
object SimpleAlgorithm {

  implicit class DoubleMax(val that: Double) extends AnyVal {
    def max(other: Double) = if (that > other) that else other
  }
}
