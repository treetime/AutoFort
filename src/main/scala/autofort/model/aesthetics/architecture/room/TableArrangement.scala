package autofort.model.aesthetics.architecture.room

trait TableArrangement

object TableArrangement {

  trait CenterFillingStrategy extends TableArrangement
  case class DivideIntoParts(parts: Int, centered: Boolean = true) extends CenterFillingStrategy
  case class FixedSpacing(width: Int) extends CenterFillingStrategy

  trait PerimeterFillingStrategy extends TableArrangement
  case class SidesFirst(groupSize: Int, thickness: Double) extends PerimeterFillingStrategy

  sealed trait PerimeterBlockType
  case object Normal extends PerimeterBlockType
  case object InternalCorner extends PerimeterBlockType// has 7 neighbors in shape
  case object ExternalCorner extends PerimeterBlockType//has 2 non-detachable neighbors
  case object Detachable extends PerimeterBlockType//doesn't break continuity when removed

}
