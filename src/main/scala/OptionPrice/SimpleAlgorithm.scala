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

  import config.{X, S, r, deltaT}

  val coef = Math.exp(-r * deltaT)

  def calcBranch(points: Seq[Point], factor: Double = 1): Double = points match {
    case Nil           => (X - factor * S) max 0
    case point :: rest => coef * {
      import point._
      pu * calcBranch(rest, factor * u) + pd * calcBranch(rest, factor * d) +
        (if (pm > 0) pm * calcBranch(rest, factor) else 0.0)
    }
  }

  override def calc: Double = calcBranch(points)
}

