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

  def calcBranch(points: Seq[Point], factor: Double = 1): Double = points match {
    case Nil => config.X - factor * config.S max 0
    case Point(p, u, d) :: other => p * u * calcBranch(other, factor * u) - (1 - p) * d * calcBranch(other, factor * d)
  }

  override def calc: Double = calcBranch(points)
}
object SimpleAlgorithm {

  implicit class DoubleMax(val that: Double) extends AnyVal {
    def max(other: Double) = if (that > other) that else other
  }
}
