package autofort.model.aesthetics.architecture.room.arrangement

import autofort.model.aesthetics.architecture.room.arrangement.ArrangementConfig.{
  CenterConfig,
  PerimeterConfig
}

//groups of what? spaced how? xx . xx . vs xxx .. xxx ..

case class ArrangementConfig(center: CenterConfig, perimeter: PerimeterConfig) {}

object ArrangementConfig {

  trait CenterConfig

  trait PerimeterConfig

  case class Rings() extends CenterConfig

  case class Rows(grouping: Int) extends CenterConfig

  case class Grid() extends CenterConfig

  case class Groups(groupSize: Int) extends PerimeterConfig

  case class WallFill() extends PerimeterConfig

  case object EmptyPerimeter extends PerimeterConfig

}
