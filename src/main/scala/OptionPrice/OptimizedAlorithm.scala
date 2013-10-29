package OptionPrice

/**
 * User: Oleg
 * Date: 10/29/13
 * Time: 3:46 AM
 */
trait OptimizedAlorithm extends Algorithm with IntervalOptimization {
  this: Calculation =>

  import config.{X, S, r, deltaT}

  val coef = Math.exp(-r * deltaT * points.size)
  val cache = new IntervalCache[Seq[Point]]

  def calcBranchSimple(points: Seq[Point], factor: Double = 1): IntervalValue = points match {
    case Nil           => new IntervalValue(onLeaf(factor)) barrier (X / S)
    case point :: rest => {
      import point._
      (calcBranch(rest, factor * u) * pu) \\ u + (calcBranch(rest, factor * d) * pd) \\ d +
        (if (pm > 0) calcBranch(rest, factor) * pm else new IntervalValue(0.0))

    }
  }

  val calcBranch = calcBranchSimple _

  override def calc: Double = coef * ( X - calcBranch(points, 1).value * S )
}
