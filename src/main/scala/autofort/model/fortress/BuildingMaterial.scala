package autofort.model.fortress

import FortressModel.{BuildingMaterial, FortressComponent}


object BuildingMaterial {

  trait Wood extends BuildingMaterial

  trait Stone extends BuildingMaterial

  trait Clay extends BuildingMaterial

  trait Glass extends BuildingMaterial

  trait Ice extends BuildingMaterial

  trait Soap extends BuildingMaterial

}