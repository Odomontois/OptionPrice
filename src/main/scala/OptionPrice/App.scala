package OptionPrice

import scala.util.{Success, Failure}

/**
 * @author ${user.name}
 */
object App extends App{
  util.Properties.setProp("scala.time","")
  println(Config.read("/config.json") match{
    case Failure(error) => Console.err.println(error)
    case Success(config) => println( (new Calculation(config) with SimpleAlgorithm).result )
  })
}
