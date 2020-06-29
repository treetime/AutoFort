package autofort.model.fortress

import autofort.model.aesthetics.architecture.ArchitectureConfig
import autofort.model.aesthetics.architecture.room.RoomDefinition
import autofort.model.fortress.FortressModel.FortressComponent

trait Shelter extends FortressComponent {

  val baseSize = 9
  val scaling: Double = (800 - 9) / 200.0

  def generate(population: Int, config: ArchitectureConfig) = {
    val scale: Int = 5
    val room: RoomDefinition = config.defineRoom(population) //spec scale from minsquarea required

  }

}

object Shelter {

  case class Keep() extends Shelter {}

  case class Cave() extends Shelter

}
