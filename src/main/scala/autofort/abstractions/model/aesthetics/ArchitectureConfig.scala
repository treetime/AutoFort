package autofort.abstractions.model.aesthetics

import autofort.abstractions.model.aesthetics.ArchitectureConfig.RoomDefinition
import autofort.abstractions.model.aesthetics.architecture.shape.AreaDefinition
import autofort.abstractions.model.aesthetics.architecture.{ArrangementStrategy, BuildingConfig, ComplexConfig, ConnectionConfig, RoomConfig}
import autofort.abstractions.model.aesthetics.arrangement.Placeable

/*trait PersonalityTrait
case object Xenophobia extends PersonalityTrait //need to limit access to the overall fortress area
case object Naturalism extends PersonalityTrait //willingness to blend into rock vs need for exact architecture
case class Personality(traits: Map[PersonalityTrait, Double])*/

case class ArchitectureConfig(buildingConfig: BuildingConfig,
                              roomConfig: RoomConfig,
                              roomGroupConfig: ComplexConfig,
                              connectionConfig: ConnectionConfig) {

  def getRoom(baseSize: Int, placeables: Seq[Placeable]): RoomDefinition = {
    val definition = roomConfig.defineRoom(baseSize, placeables)

  }

}

object ArchitectureConfig {


  case class RoomDefinition(definition: AreaDefinition)


}