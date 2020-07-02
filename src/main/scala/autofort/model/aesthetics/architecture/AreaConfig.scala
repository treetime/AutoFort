package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.room.{
  PlaceableConfig,
  RoomDefinition
}

case class AreaConfig(placementSpecs: Seq[PlaceableConfig]) {

  def generateRoom(architectureConfig: ArchitectureConfig,
                   population: Int): RoomDefinition = {
    val scale: Int = Math.sqrt(population).toInt
    val room = RoomDefinition(
      architectureConfig.preferredShape.generateArea(scale)
    )
    placementSpecs.foldLeft(room) {
      case (room, placementConfig) =>
        placementConfig.fill(room, population)
    }

  }

}

object AreaConfig {}
