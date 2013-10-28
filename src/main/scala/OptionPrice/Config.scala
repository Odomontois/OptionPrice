package OptionPrice

import scala.io.Source
import net.liftweb.json._

import scala.util.Try

/**
 * User: Oleg
 * Date: 10/27/13
 * Time: 11:44 PM
 */
case class Config private(
                           S: Double,
                           X: Double,
                           u: Seq[Double],
                           d: Option[Seq[Double]],
                           pu: Seq[Double],
                           pd: Option[Seq[Double]],
                           pm: Option[Seq[Double]],
                           r: Double,
                           deltaT: Double = 0
                           )
object Config {

  import net.liftweb.json.JsonParser._

  implicit val formats = DefaultFormats

  def read(name: String, cfg: String = "/config.json") = Try(parse(Source.fromURL(getClass.getResource(cfg)).reader
  ).extract[Map[String, Config]] apply name)
}

