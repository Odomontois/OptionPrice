package OptionPrice

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 10/28/13
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
trait SimpleAlgorithm extends Algorithm{
  this: Calculation =>
  override def calc:Double = 1.53
}
