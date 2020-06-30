package autofort.model.fortress

import autofort.model.aesthetics.architecture.{ArchitectureConfig, AreaConfig}
import autofort.model.aesthetics.architecture.Specification.MinMax
import autofort.model.aesthetics.architecture.room.{PlaceableConfig, RoomDefinition}
import autofort.model.fortress.FortressModel.FortressComponent
import autofort.model.placeables.Placeable._

trait Shelter extends FortressComponent {

  val baseSize = 9
  val areaScaling: Double = (800 - 9) / 200.0
  val areaConfig: AreaConfig = AreaConfig()

  val placeableConfig: PlaceableConfig = PlaceableConfig(
    Throne() -> MinMax(4,)
  )

  def generate(population: Int, config: ArchitectureConfig): RoomDefinition = {
    val scale: Int = 5
    val room: RoomDefinition = config.defineRoom(population)

    ???
  }

}

object Shelter {

  case class Keep() extends Shelter {}

  case class Cave() extends Shelter

}
