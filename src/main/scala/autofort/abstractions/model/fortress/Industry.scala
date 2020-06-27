package autofort.abstractions.model.fortress

import FortressModel.FortressComponent

trait Industry extends FortressComponent

object Industry {

  trait Woodcrafting extends Industry
  trait Stonecrafting extends Industry //requires cave
  trait AnimalFarm extends Industry
  trait Leatherworking extends Industry
  trait Glasscrafting extends Industry
  trait Bonecarving extends Industry
  trait Gemcrafting extends Industry //requires cave

}
