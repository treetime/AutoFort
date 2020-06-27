package autofort.generator

import autofort.abstractions.map.GridMap
import autofort.abstractions.model.aesthetics.StyleGuide
import autofort.abstractions.model.embark.Embark
import autofort.abstractions.model.fortress.FortressModel
import autofort.abstractions.model.fortress.fortress.FortressComponent

trait Area

class Fortress(embark: Embark, grid: GridMap, model: FortressModel, style: StyleGuide) {
  val population: Int = 0

}

object Fortress {

  val population = 0



}