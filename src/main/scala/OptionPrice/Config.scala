package OptionPrice

import scala.io.Source
import net.liftweb.json._

import scala.util.Try

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 10/27/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
case class Config private(
                   x:Double,
                   u: Seq[Double],
                   p: Seq[Double],
                   r: Double,
                   deltaT: Double = 0
)
object Config{
  import net.liftweb.json.JsonParser._
  implicit val formats = DefaultFormats
  def read(filename:String) =  Try(Some(parse(Source.fromFile(filename).reader).extract[Config]))
}
