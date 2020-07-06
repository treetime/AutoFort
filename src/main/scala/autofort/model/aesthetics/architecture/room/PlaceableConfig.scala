package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.Specification

abstract class PlaceableConfig(val arrange: ArrangementConfig,
                               val specification: Specification) {
  def fill(roomDefinition: RoomDefinition, population: Int): RoomDefinition = {
    fill(roomDefinition, specification.specify(population))
  }

  def fill(roomDefinition: RoomDefinition, spec: Double): RoomDefinition

}

object PlaceableConfig {

  case class DiningRoomSet(val plan: TableArrangement,
                           override val specification: Specification)
      extends PlaceableConfig(plan, specification) {

    def fill(room: RoomDefinition, spec: Double): RoomDefinition = {
      val filledAreas = room.area.subSpaces.rectangularAreas
        .map(area => plan.fill(area, spec))
      RoomDefinition(new AreaDefinition(filledAreas.foldLeft(room.area.blocks) {
        case (blocks, rect) =>
          blocks.toSeq.map { block =>
            rect.blocks.find(block.areaCompare) match {
              case Some(friend) => friend
              case None         => block
            }
          }.toSet
      }))
    }

  }

}
