package OptionPrice

/**
 * User: Oleg
 * Date: 10/29/13
 * Time: 3:46 AM
 */
trait OptimizedAlgorithm extends Algorithm with IntervalOptimization {
  this: Calculation =>

  import config.{X, S, r, deltaT}

  override val barrier = X / S

  val coef = Math.exp(-r * deltaT * points.size)
  val gCache = new IntervalCache[Seq[Point]]
  val hCache = new IntervalCache[Seq[Point]]

  def calcG(points: Seq[Point], factor: Double = 1): IntervalValue = onNode(points) match {
    case Nil           => factor.leaf
    case point :: rest => {
      import point._
      ((gFunc(rest, factor * u) * pu * u) \\ u) + ((gFunc(rest, factor * d) * pd * d) \\ d) + (gFunc(rest, factor) * pm)
    }
  }

  def calcH(points: Seq[Point], factor: Double = 1): IntervalValue = onNode(points) match {
    case Nil           => factor.leaf
    case point :: rest => {
      import point._
      ((hFunc(rest, factor * u) * pu) \\ u) + ((hFunc(rest, factor * d) * pd) \\ d) + (hFunc(rest, factor) * pm)
    }
  }

  def calcProd(points: Seq[Point]): Double = points match {
    case Nil           => S
    case point :: rest => {
      import point._
      (pu * u + pd * d + pm) * calcProd(rest)
    }
  }

  val gFunc = gCache(calcG _)
  val hFunc = hCache(calcH _)

  override def calc: Double = coef * (X - calcProd(points) + S * gFunc(points, 1).value - X * hFunc(points, 1).value)
}
