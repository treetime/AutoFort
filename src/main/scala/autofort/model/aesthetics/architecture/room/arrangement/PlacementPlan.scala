package autofort.model.aesthetics.architecture.room.arrangement

import autofort.model.aesthetics.architecture.room.arrangement.PlacementPlan.Space

class PlacementPlan(priority: Int, position: Space)

object PlacementPlan {

  trait Space
  case object Perimeter extends Space
  case object Center extends Space

}
