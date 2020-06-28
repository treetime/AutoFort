package autofort.model.fortress

import autofort.model.fortress.FortressModel.FortressComponent

trait Medical extends FortressComponent

object Medical {

  trait WaterSource extends Medical
  trait Hospital extends Medical

}
