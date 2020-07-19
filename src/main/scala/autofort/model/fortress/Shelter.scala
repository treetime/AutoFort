package autofort.model.fortress

import autofort.model.aesthetics.architecture.Specification.FillPercent
import autofort.model.aesthetics.architecture.room.PlaceableConfig.DiningRoomSet
import autofort.model.aesthetics.architecture.room.RoomDefinition
import autofort.model.aesthetics.architecture.room.TableArrangement.Lines
import autofort.model.aesthetics.architecture.{ArchitectureConfig, AreaConfig}
import autofort.model.aesthetics.preferences.Alignment
import autofort.model.fortress.FortressModel.FortressComponent

class Shelter extends FortressComponent {

  val baseSize = 9
  val areaScaling: Double = (800 - 9) / 200.0
  //val areaConfig: AreaConfig = AreaConfig()

  private val areaConfig = AreaConfig(
    Seq(DiningRoomSet(Lines(Alignment.LATERAL), FillPercent(1, 0.8)))
  )

  def generate(scale: Int,
               architecture: ArchitectureConfig): RoomDefinition = {
    areaConfig.generateRoom(architecture, scale)
  }

}

object Shelter {

  case class Keep() extends Shelter {}

  case class Cave() extends Shelter

}
