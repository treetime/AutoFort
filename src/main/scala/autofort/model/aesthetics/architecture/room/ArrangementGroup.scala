package autofort.model.aesthetics.architecture.room

trait ArrangementGroup {}

object ArrangementGroup {

  sealed trait FillStrategy

  case class TableGroup() extends ArrangementGroup

  case class HalfFirst() extends FillStrategy
  case class Alternating() extends FillStrategy
  case class OppositeCorners() extends FillStrategy

}
