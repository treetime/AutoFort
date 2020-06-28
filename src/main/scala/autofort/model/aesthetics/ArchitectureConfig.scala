package autofort.model.aesthetics

import autofort.model.aesthetics.ArchitectureConfig.RoomDefinition
import autofort.model.aesthetics.architecture.arrangement.ArrangementConfig
import autofort.model.aesthetics.architecture.shape.AreaDefinition
import autofort.model.aesthetics.architecture.{
  ConnectionConfig,
  PreferredGeometry,
  PreferredShape
}
import autofort.model.fortress.arrangement.ArrangementGroup.FunctionalSet

/*trait PersonalityTrait
case object Xenophobia extends PersonalityTrait //need to limit access to the overall fortress area
case object Naturalism extends PersonalityTrait //willingness to blend into rock vs need for exact architecture
case class Personality(traits: Map[PersonalityTrait, Double])*/

case class ArchitectureConfig(preferredShape: PreferredShape,
                              preferredGeometry: PreferredGeometry,
                              connectionConfig: ConnectionConfig) {
  def defineRoom(scale: Int): RoomDefinition = {
    preferredShape.generateRoom(scale)
  }
}

object ArchitectureConfig {

  case class RoomDefinition(area: AreaDefinition) {
    def place(arrangementConfig: ArrangementConfig,
              placeable: Seq[FunctionalSet]): Unit = {
      val subSpaces = area.center.subSpaces()
      placeable.sortBy(_.priority).foldLeft(area){case (remaining, set) =>  }

    }
  }

}
