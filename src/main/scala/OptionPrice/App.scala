package OptionPrice

import scala.util.{Success, Failure}

/**
 * @author ${user.name}
 */
object App extends App {
  util.Properties.setProp("scala.time", "")
  println(Config.read("base") match {
    case Failure(error)  => Console.err.println(error)
    case Success(config) => {
      val simple = new Calculation(config) with SimpleAlgorithm
      val optimized = new Calculation(config) with OptimizedAlorithm
      println(optimized.result, optimized.leafs)
      println(simple.result, simple.leafs, simple.nullleafs)


    }

  })
}
