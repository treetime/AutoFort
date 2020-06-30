package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.room.PlacementPlan.Space

abstract class PlaceableConfig(arrange: ArrangementConfig, priority: Int)

object PlaceableConfig {

  class DiningRoomSet(plan: TableArrangement, priority: Int) extends PlaceableConfig(plan, priority) {

  }


}
