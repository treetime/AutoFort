package autofort.model.embark

import autofort.model.embark.Embark.Reason

/**
 * Global need for a massive monument in the jungle :p
 * */


case class Embark(nDwarves: Int, reason: Reason)

object Embark {
  trait Problem
  case object TOO_MUCH extends Problem
  case object TOO_LITTLE extends Problem

  trait Target
  trait Substance extends Target
  trait MentalState extends Target
  trait EmbarkProperty extends Target

  trait GlobalNeed

  case class Imbalance(problem: Problem, target: Target, intensity: Int) extends GlobalNeed
  case class Warfare() extends GlobalNeed
  case class Wonder() extends GlobalNeed

  case class Reason(needs: Seq[GlobalNeed])


}
