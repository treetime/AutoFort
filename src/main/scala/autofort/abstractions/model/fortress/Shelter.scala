package autofort.abstractions.model.fortress

import autofort.abstractions.model.aesthetics.arrangement.placeables.ArrangementGroup.TableSet
import autofort.abstractions.model.aesthetics.{ArchitectureConfig, StyleGuide}
import autofort.abstractions.model.aesthetics.arrangement.placeables.Furniture.{Table, Throne}
import autofort.util.Rounding._

trait Shelter extends FortressModel {

  val baseSize = 9
  val scaling: Double = (800 - 9) / 200.0


  def generateElements(population: Int, config: ArchitectureConfig) = {
    val tableSets = 2 + population / 3
    val doors = 1

    val placeables = (0 until tableSets).map(_ => TableSet())

    val room = config.getRoom((baseSize + population * scaling).makeInt, placeables)


  }


}

object Shelter {

  def generate(population: Int, style: StyleGuide) = {

  }

  case class Keep() extends Shelter {

  }

  case class Cave() extends Shelter


}


