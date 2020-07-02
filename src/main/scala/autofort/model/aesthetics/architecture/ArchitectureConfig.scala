package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.room.RoomDefinition
import autofort.model.map.AreaDefinition
/*trait PersonalityTrait
case object Xenophobia extends PersonalityTrait //need to limit access to the overall fortress area
case object Naturalism extends PersonalityTrait //willingness to blend into rock vs need for exact architecture
case class Personality(traits: Map[PersonalityTrait, Double])*/

case class ArchitectureConfig(preferredGeometry: PreferredGeometry,
                              preferredShape: PreferredShape,
                              connectionConfig: ConnectionConfig)

object ArchitectureConfig {}
