package autofort.model.aesthetics.architecture.room.arrangement

trait ArrangementGroup {

}

object ArrangementGroup {


  case class TableGroup() extends ArrangementGroup


  sealed trait FillStrategy
  case class HalfFirst() extends FillStrategy
  case class Alternating() extends FillStrategy
  case class OppositeCorners() extends FillStrategy

}
