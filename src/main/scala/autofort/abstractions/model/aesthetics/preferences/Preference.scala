package autofort.abstractions.model.aesthetics.preferences

import autofort.abstractions.model.aesthetics.preferences.Preference.Preferable

case class Preference[T](p: Preferable[T], intensity: Double) //0 to 1

object Preference {
  trait Preferable[T] {
    def getChoices: Seq[T]
  }
}
