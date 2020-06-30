package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.room.{ArrangementConfig, PlaceableConfig, RoomDefinition}
import autofort.model.map.AreaDefinition
import autofort.model.items.Item
/*trait PersonalityTrait
case object Xenophobia extends PersonalityTrait //need to limit access to the overall fortress area
case object Naturalism extends PersonalityTrait //willingness to blend into rock vs need for exact architecture
case class Personality(traits: Map[PersonalityTrait, Double])*/

case class ArchitectureConfig(preferredGeometry: PreferredGeometry,
                              preferredShape: PreferredShape,
                              connectionConfig: ConnectionConfig) {
  def defineRoom(population: Int): RoomDefinition = {
    val scale: Int = Math.sqrt(population).toInt
    val placeables: Seq[Item] = placeableConfig.generate(population)
    val definition: AreaDefinition = preferredShape.generateArea(scale)
    //val room: RoomDefinition = arrangementConfig.arrange(definition, items)
    ???

  }
}

object ArchitectureConfig {



}
