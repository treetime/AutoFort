package autofort.generator

import autofort.model.map.GridMap
import autofort.model.aesthetics.StyleGuide
import autofort.model.embark.Embark
import autofort.model.fortress.FortressModel
import autofort.model.fortress.fortress.FortressComponent

trait Area

class Fortress(embark: Embark, grid: GridMap, model: FortressModel, style: StyleGuide) {
  val population: Int = 0

}

object Fortress {

  val population = 0



}