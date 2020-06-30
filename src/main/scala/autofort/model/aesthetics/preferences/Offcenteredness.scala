package autofort.model.aesthetics.preferences

import autofort.model.aesthetics.preferences.Preference.Preferable

trait Offcenteredness

object Offcenteredness extends Preferable[Offcenteredness] {

  case object UP_LEFT extends Offcenteredness
  case object UP_RIGHT extends Offcenteredness
  case object DOWN_LEFT extends Offcenteredness
  case object DOWN_RIGHT extends Offcenteredness
  case object RADIALLY_OUT extends Offcenteredness
  case object RADIALLY_IN extends Offcenteredness
  case object RECTANGULAR_OUT extends Offcenteredness
  case object RECTANGULAR_IN extends Offcenteredness

  override def getChoices: Seq[Offcenteredness] = Seq(
    UP_LEFT,
      UP_RIGHT,
      DOWN_LEFT,
      DOWN_RIGHT,
      RADIALLY_OUT,
      RADIALLY_IN,
      RECTANGULAR_OUT,
      RECTANGULAR_IN
  )
}