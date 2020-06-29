package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.room.{ArrangementConfig, PlaceableConfig, RoomDefinition}
import autofort.model.aesthetics.architecture.shape.AreaDefinition
/*trait PersonalityTrait
case object Xenophobia extends PersonalityTrait //need to limit access to the overall fortress area
case object Naturalism extends PersonalityTrait //willingness to blend into rock vs need for exact architecture
case class Personality(traits: Map[PersonalityTrait, Double])*/

case class ArchitectureConfig(areaConfig: AreaConfig,
                              placeableConfig: PlaceableConfig,
                              arrangementConfig: ArrangementConfig,
                              preferredGeometry: PreferredGeometry,
                              preferredShape: PreferredShape,
                              connectionConfig: ConnectionConfig) {
  def defineRoom(population: Int): RoomDefinition = {
    val scale: Int = Math.sqrt(population).toInt
    val placeables = placeableConfig.generate(population)
    val definition = preferredShape.generateRoom(scale)
    //val arranged = definition.place(placeables, arrangementConfig)
    AreaDefinition(Set.empty, true)
    ???
  }
}

object ArchitectureConfig {



}
