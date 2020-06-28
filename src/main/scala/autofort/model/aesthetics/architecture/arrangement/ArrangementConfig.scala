package autofort.model.aesthetics.architecture.arrangement

import autofort.model.aesthetics.architecture.AreaConfig
import autofort.model.aesthetics.architecture.arrangement.ArrangementConfig.{CenterConfig, PerimeterConfig}
import autofort.model.aesthetics.architecture.shape.AreaDefinition

//groups of what? spaced how? xx . xx . vs xxx .. xxx ..

case class ArrangementConfig(center: CenterConfig, perimeter: PerimeterConfig)

object ArrangementConfig {

  trait CenterConfig {
    def parition(area: AreaDefinition): (AreaDefinition, AreaDefinition) = {
      area.center.subSpaces()
    }
  }

  trait PerimeterConfig

  case class Rings() extends CenterConfig

  case class Rows(grouping: Int) extends CenterConfig

  case class Grid() extends CenterConfig

  case class Groups(groupSize: Int) extends PerimeterConfig

  case class WallFill() extends PerimeterConfig

  case object EmptyPerimeter extends PerimeterConfig

}
