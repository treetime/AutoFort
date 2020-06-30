package autofort.model.aesthetics.architecture.room

sealed trait PerimeterBlockType

case object Normal extends PerimeterBlockType
case object InternalCorner extends PerimeterBlockType // has 7 neighbors in shape
case object ExternalCorner extends PerimeterBlockType //has 2 non-detachable neighbors
case object Detachable extends PerimeterBlockType //doesn't break continuity when removed
