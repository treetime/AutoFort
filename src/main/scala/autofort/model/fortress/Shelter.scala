package autofort.model.fortress

import autofort.model.aesthetics.ArchitectureConfig.RoomDefinition
import autofort.model.aesthetics.architecture.RoomConfig.{MinMax, PlaceableConfig}
import autofort.model.aesthetics.architecture.arrangement.ArrangementConfig
import autofort.model.aesthetics.architecture.arrangement.ArrangementConfig.{EmptyPerimeter, Rows}
import autofort.model.aesthetics.architecture.{AreaConfig, RoomConfig}
import autofort.model.aesthetics.{ArchitectureConfig, StyleGuide}
import autofort.model.fortress.FortressModel.FortressComponent
import autofort.model.fortress.arrangement.ArrangementGroup.DiningSet
import autofort.model.fortress.arrangement.placeables.Furniture.Table
import autofort.util.Rounding._

trait Shelter extends FortressComponent {

  val baseSize = 9
  val scaling: Double = (800 - 9) / 200.0

  val room: RoomConfig = Shelter.room
  val arrangementStrategy: ArrangementConfig


  def generate(population: Int, config: ArchitectureConfig) = {
    val scale: Int = 5
    val placeables = Shelter.generatePlaceables(population)
    val room: RoomDefinition = config.defineRoom(scale) //spec scale from minsquarea required

  }


}

object Shelter {

  val room: RoomConfig = RoomConfig(
    AreaConfig(
      MinMax(3*3, 11 * 33)
    ),
    PlaceableConfig(
      DiningSet() -> MinMax(4, 80)
    ),
    ArrangementConfig(
      Rows(6),
      EmptyPerimeter
    )
  )

  def generatePlaceables(population: Int) = {
    room.placeableConfig.generate(population)
  }

  case class Keep() extends Shelter {



  }

  case class Cave() extends Shelter


}


