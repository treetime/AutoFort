package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.room.PlacementPlan.Space

class PlacementPlan(priority: Int, position: Space)

object PlacementPlan {

  trait Space
  case object Perimeter extends Space
  case object Center extends Space

}
