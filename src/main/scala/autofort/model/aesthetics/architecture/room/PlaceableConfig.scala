package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.Specification
import autofort.model.map.AreaDefinition

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

    def fill(roomDefinition: RoomDefinition, spec: Double): RoomDefinition = {
      roomDefinition.area.center.subSpaces.rectangles.foldLeft(roomDefinition) {
        case (room, (size, areas)) =>
          areas.foldLeft(room) {
            case (r, fillArea) =>
              RoomDefinition(
                AreaDefinition(r.area.replaceWith(plan.fill(fillArea, spec)))
              )
          }
      }
    }

  }

}
