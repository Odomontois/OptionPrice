package OptionPrice

import scala.util.{Success, Failure}

/**
 * @author ${user.name}
 */
object App extends App {
  util.Properties.setProp("scala.time", "")
  println(Config.read("modified") match {
    case Failure(error)  => Console.err.println(error)
    case Success(config) => {
      val simple = new Calculation(config) with SimpleAlgorithm
      val optimized = new Calculation(config) with OptimizedAlgorithm
      println(optimized.result, optimized.leafs, optimized.nullleafs)
      println(simple.result, simple.leafs, simple.nullleafs)


    }

  })
}
