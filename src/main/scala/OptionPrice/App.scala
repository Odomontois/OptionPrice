package OptionPrice

import scala.util.{Success, Failure}

object App extends App {
  util.Properties.setProp("scala.time", "")
  println(Config.read("modified") match {
    case Failure(error)  => Console.err.println(error)
    case Success(config) => {
      val simple = new Calculation(config) with SimpleAlgorithm
      val optimized = new Calculation(config) with OptimizedAlgorithm
      println(optimized.result, optimized.nodes)
      println(simple.result, simple.nodes)
    }
  })
}
