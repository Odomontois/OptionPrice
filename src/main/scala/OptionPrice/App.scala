package OptionPrice

import java.io.File
import scala.util.{Success, Failure}

/**
 * @author ${user.name}
 */
object App extends App{
  println(Config.read("OptionPrice/config.json") match{
    case Failure(error) => Console.err.println(error)
    case Success(config) =>
  })
}
