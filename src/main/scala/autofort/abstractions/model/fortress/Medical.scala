package autofort.abstractions.model.fortress

import autofort.abstractions.model.fortress.FortressModel.FortressComponent

trait Medical extends FortressComponent

object Medical {

  trait WaterSource extends Medical
  trait Hospital extends Medical

}
