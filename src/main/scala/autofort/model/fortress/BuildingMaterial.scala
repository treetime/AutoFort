package autofort.model.fortress

import autofort.model.fortress.FortressModel.FortressComponent
trait BuildingMaterial extends FortressComponent

object BuildingMaterial {

  trait Wood extends BuildingMaterial

  trait Stone extends BuildingMaterial

  trait Clay extends BuildingMaterial

  trait Glass extends BuildingMaterial

  trait Ice extends BuildingMaterial

  trait Soap extends BuildingMaterial

}
