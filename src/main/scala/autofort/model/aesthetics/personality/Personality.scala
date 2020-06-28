package autofort.model.aesthetics.personality

case class Personality ()

object Personality {

  trait PersonalityTrait

  case class Cavedweller(intensity: Double) //-1 = strict surfacedwaller, min length vertical shafts

  case class Opulent(intensity: Double) //-1 = frugal

}
