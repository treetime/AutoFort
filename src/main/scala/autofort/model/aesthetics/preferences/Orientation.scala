package autofort.model.aesthetics.preferences

import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{DOWN, Direction, LEFT, RIGHT, UP}
import autofort.model.aesthetics.preferences.Preference.Preferable

trait Orientation

object Orientation extends Preferable[Orientation] {
  case object HORIZONTAL extends Orientation
  case object VERTICAL extends Orientation

  override def getChoices: Seq[Orientation] = Seq(HORIZONTAL, VERTICAL)
}


trait Alignment

object Alignment extends Preferable[Alignment] {
  case object LATERAL extends Alignment
  case object LONGITUDINAL extends Alignment

  def fromDirection(d: Direction): Alignment = d match {
    case LEFT | RIGHT => LATERAL
    case UP | DOWN => LONGITUDINAL
  }

  override def getChoices: Seq[Alignment] = Seq(LATERAL, LONGITUDINAL)
}