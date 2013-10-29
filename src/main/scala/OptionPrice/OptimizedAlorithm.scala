package OptionPrice

/**
 * User: Oleg
 * Date: 10/29/13
 * Time: 3:46 AM
 */
trait OptimizedAlorithm extends Algorithm with IntervalOptimization {
  this: Calculation =>

  import config.{X, S, r, deltaT}

  override val barrier = X / S

  val coef = Math.exp(-r * deltaT * points.size)
  val cache = new IntervalCache[Seq[Point]]

  def calcBranchSimple(points: Seq[Point], factor: Double = 1): IntervalValue = points match {
    case Nil           => onLeaf(factor).leaf
    case point :: rest => {
      import point._
      (calcBranch(rest, factor * u) * pu) \\ u + (calcBranch(rest, factor * d) * pd) \\ d + (calcBranch(rest, factor) * pm)
    }
  }

  def calcProd(points: Seq[Point]): Double = points match {
    case Nil           => S
    case point :: rest => {
      import point._
      (pu * u + pd * d + pm) * calcProd(rest)
    }
  }

  val calcBranch = cache(calcBranchSimple _)

  override def calc: Double = coef * (X - calcProd(points) + calcBranchSimple(points).value)
}
