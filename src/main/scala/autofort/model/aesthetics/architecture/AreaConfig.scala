package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.room.{
  PlaceableConfig,
  RoomDefinition
}

case class AreaConfig(placementSpecs: Seq[PlaceableConfig]) {

  def generateRoom(architectureConfig: ArchitectureConfig,
                   population: Int): RoomDefinition = {
    val scale: Int = Math.round(3 + 40 * (population - 7) / 193.9).toInt
    architectureConfig.preferredShape.generateArea(scale)
  }

}

object AreaConfig {}
