package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.room.ArrangementConfig.{CenterConfig, PerimeterConfig}

//groups of what? spaced how? xx . xx . vs xxx .. xxx ..

trait ArrangementConfig

object ArrangementConfig {

  trait CenterConfig extends ArrangementConfig

  trait PerimeterConfig extends ArrangementConfig

  case class Rings() extends CenterConfig

  case class Rows(grouping: Int) extends CenterConfig

  case class Grid() extends CenterConfig

  case class Groups(groupSize: Int) extends PerimeterConfig

  case class WallFill() extends PerimeterConfig

  case object EmptyPerimeter extends PerimeterConfig

}
