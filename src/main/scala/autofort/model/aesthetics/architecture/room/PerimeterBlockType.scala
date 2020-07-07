package autofort.model.aesthetics.architecture.room

sealed trait GridBlockClassification

case object WallBlockType extends GridBlockClassification

case class AreaBlockType(p: Int = 0) extends GridBlockClassification

sealed trait PerimeterBlockType extends GridBlockClassification

case object PerimeterBlock extends PerimeterBlockType
case object InternalCorner extends PerimeterBlockType // has 7 neighbors in shape
case object ExternalCorner extends PerimeterBlockType //has 2 non-detachable neighbors
case object Detachable extends PerimeterBlockType //doesn't break continuity when removed
