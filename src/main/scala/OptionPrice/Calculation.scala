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
abstract class Calculation(config: Config) extends Algorithm {
  lazy val result = calc
}


