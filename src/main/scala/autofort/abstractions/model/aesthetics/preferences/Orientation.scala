package autofort.abstractions.model.aesthetics.preferences

import autofort.abstractions.model.aesthetics.preferences.Preference.Preferable

trait Orientation

object Orientation extends Preferable[Orientation] {
  case object HORIZONTAL extends Orientation
  case object VERTICAL extends Orientation

  override def getChoices: Seq[Orientation] = Seq(HORIZONTAL, VERTICAL)
}

