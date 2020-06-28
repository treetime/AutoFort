package autofort.model.aesthetics.preferences

import autofort.model.aesthetics.preferences.Preference.Preferable

trait Orientation

object Orientation extends Preferable[Orientation] {
  case object LATERAL extends Orientation
  case object LONGITUDINAL extends Orientation

  override def getChoices: Seq[Orientation] = Seq(LATERAL, LONGITUDINAL)
}

