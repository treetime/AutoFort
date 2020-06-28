package autofort.model.fortress

import autofort.model.aesthetics.StyleGuide
import FortressModel.FortressComponent

class FortressModel() {
  var ids: Set[Int] = Set.empty
  var components: Set[FortressComponent] = Set.empty

  def getId: Int = {
    val newId = (0 until ids.size).filter(ids).min
    ids = ids + newId
    newId
  }

  def addComponent(component: FortressComponent): Unit = components = components + component


}

object FortressModel {

  trait FortressComponent {
    val id: Int

    def generate(population: Int, style: StyleGuide): FortressComponent

  }


  object FortPriorityOrder extends Enumeration {
    type FortPriorityOrder = Value
    val Shelter,
    Security,
    Food,
    BuildingMaterial,
    SleepingArea,
    Industry,
    Medical,
    Recreation,
    Beauty = Value
  }




}