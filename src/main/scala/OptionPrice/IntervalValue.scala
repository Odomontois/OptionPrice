package OptionPrice

import scala.collection.mutable
import java.util


/**
 * User: Oleg
 * Date: 10/29/13
 * Time: 1:51 AM
 */
trait IntervalOptimization {
  val barrier: Double
  case class Interval private[Interval](val left: Double, val right: Double) {
    def /\(other: Interval) = Interval.create(left max other.left, right min other.right)

    def contains(element: Double) = element >= left && element <= right

    def \\(factor: Double) = new Interval(left / factor, right / factor)
  }
  object Interval {

    import Double.{PositiveInfinity => Inf, NegativeInfinity => NInf}

    val whole = new Interval(NInf, Inf)
    val empty = new Interval(Inf, NInf)

    def from(a: Double) = new Interval(a, Inf)

    def to(a: Double) = new Interval(NInf, a)

    def create(left: Double, right: Double) = if (left > right) empty else new Interval(left, right)

  }

  case class IntervalValue(value: Double, int: Interval = Interval.whole) {
    def +(other: IntervalValue) = new IntervalValue(this.value + other.value, this.int /\ other.int)

    def *(factor: Double) = new IntervalValue(this.value * factor, this.int)

    def \\(factor: Double) = new IntervalValue(this.value, this.int \\ factor)
  }

  implicit class LeafEnhance(value: Double) {
    def leaf = if (this.value < barrier)
      new IntervalValue(0, Interval.to(barrier))
    else
      new IntervalValue(1, Interval.from(barrier))
  }

  class IntervalCache[Level] {
    val map = mutable.Map.empty[Level, util.TreeMap[Double, IntervalValue]]
    type Func = (Level, Double) => IntervalValue

    def apply(func: Func): Func = (level, element) => {
      val treemap = map.getOrElseUpdate(level, new util.TreeMap)
      val entry = treemap.floorEntry(element)
      if (entry == null || !(entry.getValue.int contains element)) {
        val result = func(level, element)
        treemap.put(result.int.left, result)
        result
      } else
        entry.getValue
    }

  }
}

