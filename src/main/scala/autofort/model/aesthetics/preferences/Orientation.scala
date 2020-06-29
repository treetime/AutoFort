package autofort.model.aesthetics.preferences

import autofort.model.aesthetics.preferences.Preference.Preferable

trait Orientation

object Orientation extends Preferable[Orientation] {
  case object HORIZONTAL extends Orientation
  case object VERTICAL extends Orientation

  override def getChoices: Seq[Orientation] = Seq(HORIZONTAL, VERTICAL)
}

trait Shapeliness

object Shapeliness extends Preferable[Shapeliness] {
  case object FAT extends Shapeliness
  case object THIN extends Shapeliness
  case object SQUARE extends Shapeliness

  override def getChoices: Seq[Shapeliness] = Seq(FAT, THIN, SQUARE)
}

trait Alignment

object Alignment extends Preferable[Alignment] {
  case object LATERAL extends Alignment
  case object LONGITUDINAL extends Alignment

  override def getChoices: Seq[Alignment] = Seq(LATERAL, LONGITUDINAL)
}