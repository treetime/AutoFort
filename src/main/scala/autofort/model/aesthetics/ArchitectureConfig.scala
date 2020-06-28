package autofort.model.aesthetics

import autofort.model.aesthetics.ArchitectureConfig.RoomDefinition
import autofort.model.aesthetics.architecture.RoomConfig.PlaceableConfig
import autofort.model.aesthetics.architecture.arrangement.ArrangementConfig
import autofort.model.aesthetics.architecture.shape.AreaDefinition
import autofort.model.aesthetics.architecture.{AreaConfig, ConnectionConfig, PreferredGeometry, PreferredShape}
import autofort.model.aesthetics.architecture.arrangement.placeables.Placeable

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
    val arranged = definition.place(placeables, arrangementConfig)
  }
}

object ArchitectureConfig {

  case class RoomDefinition(area: AreaDefinition) {
    def place(placeable: Seq[Placeable], arrangementConfig: ArrangementConfig): Unit = {
      val subSpaces = area.center.subSpaces()
      placeable.sortBy(_.priority).foldLeft(area) { case (remaining, set) =>
        arrangementConfig.center.parition(remaining)
      }
    }
  }

}
