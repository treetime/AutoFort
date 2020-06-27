package autofort.abstractions.model.aesthetics.architecture

import autofort.abstractions.model.aesthetics.ArchitectureConfig.RoomDefinition
import autofort.abstractions.model.aesthetics.arrangement.Placeable

case class RoomConfig(preferredShape: PreferredShape, arrangementStrategy: ArrangementStrategy) { //rules for making rooms
 def defineRoom(baseSize: Int, placeables: Seq[Placeable]): RoomDefinition = {
   val size = baseSize + placeables.map(_.size).sum
   val room = preferredShape.generateRoom(size)

 }
}

