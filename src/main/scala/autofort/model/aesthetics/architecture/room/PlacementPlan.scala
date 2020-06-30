package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.room.PlacementPlan.Space

trait PlacementPlan

object PlacementPlan {

  trait Space
  case object Perimeter extends Space
  case object Center extends Space

}
