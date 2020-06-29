package autofort.model.aesthetics.architecture.room

import autofort.model.placeables.Placeable
import autofort.model.aesthetics.architecture.shape.AreaDefinition

case class RoomDefinition(area: AreaDefinition) {
  def place(placeable: Seq[Placeable], arrangementConfig: ArrangementConfig): Unit = {
    val subSpaces = area.center.subSpaces()
    placeable.sortBy(_.priority).foldLeft(area) { case (remaining, set) =>
      arrangementConfig.center.parition(remaining)
    }
  }
}


object RoomDefinition {

}
