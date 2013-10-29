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

  def onLeaf(leaf: Double): Double = leaf
}


abstract class Calculation(protected val config: Config) extends Algorithm {
  protected[this] case class Point(val u: Double, val d: Double, val pu: Double, val pm: Double, val pd: Double)
  val points = {
    val pus = config.pu
    val pds = config.pd match {
      case None      => pus map (1 - _)
      case Some(pds) => pds ensuring pds.length == pus.length
    }
    val pms = (config.pd, config.pm) match {
      case (None, None)           => List.fill(pus.length)(0.0)
      case (Some(pds), None)      => pus zip pds map {
        case (u, d) => 1 - u - d ensuring (_ > 0)
      }
      case (Some(pds), Some(pms)) => pms ensuring pms.length == pus.length ensuring (pus zip pds zip pms forall ({
        case ((u, d), m) => u + d + m == 1.0 /* Т.к. числа задаются в JSON десятичными дробями можно требовать точного равенства -
                                                 Ошибки округления scala зашлифует сама */
      }))
      case _                      => sys.error("")
    }
    val us = config.u ensuring (_.length == pus.length)
    val ds = config.d match {
      case None     => us map (1 / _)
      case Some(ds) => ds ensuring ds.length == us.length
    }
    pus zip pds zip pms zip us zip ds map {
      case ((((pu, pd), pm), u), d) => Point(pu = pu, pd = pd, pm = pm, u = u, d = d)
    }
  }

  lazy val result = calc
  var leafs = 0
  var nullleafs = 0

  override def onLeaf(leaf: Double) = {
    leafs += 1
    if(leaf == 0) nullleafs += 1
    leaf
  }
}


