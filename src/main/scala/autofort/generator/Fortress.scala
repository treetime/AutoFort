package autofort.generator

import autofort.model.aesthetics.StyleGuide
import autofort.model.aesthetics.architecture.PreferredShape.Rectangle
import autofort.model.aesthetics.architecture.{
  ArchitectureConfig,
  ConnectionConfig,
  WithNoConcernForTheFuture
}
import autofort.model.embark.Embark
import autofort.model.fortress.{FortressModel, Shelter}
import autofort.model.map.GridMap

class Fortress(embark: Embark,
               grid: GridMap,
               model: FortressModel,
               style: StyleGuide) {
  val population: Int = 0

}

object Fortress extends App {

  val population = 100

  val conf = ArchitectureConfig(
    WithNoConcernForTheFuture(),
    Rectangle(),
    ConnectionConfig()
  )

  println(new Shelter().generate(population, conf).toString)

}
