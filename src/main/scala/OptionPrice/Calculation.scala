package OptionPrice

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 10/28/13
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
trait Algorithm {
  def calc: Double
}


abstract class Calculation(protected val config: Config) extends Algorithm {
  protected[this] case class Point(val prob: Double, val u: Double, val d: Double)
  val points = config.p zip config.u map (i => new Point(i._1, i._2, 1 / i._2))
  lazy val result = calc
}


