package autofort.model.fortress

import autofort.model.fortress.FortressModel.FortressComponent

trait Beauty extends FortressComponent

object Beauty {

  trait WaterFeature extends Beauty

  trait Arboretum extends Beauty //trait Plantation extends Woodcrafting
  trait Smoothing extends Beauty

  trait Road extends Beauty

  trait LandShaping extends Beauty //channeling rough edges, architecture config
}
