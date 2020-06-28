package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.BuildingConfig.Foundations

case class BuildingConfig(foundations: Foundations) //rules for making above-ground buildings

object BuildingConfig {

  case class Foundations(oversize: Int, height: Int, depth: Int)


}