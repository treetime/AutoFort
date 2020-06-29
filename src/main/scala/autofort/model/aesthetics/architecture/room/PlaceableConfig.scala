package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.Specification
import autofort.model.placeables.Placeable

case class PlaceableConfig(specs: (Placeable, Specification)*) {
  def generate(population: Int): Seq[Placeable] = specs.flatMap {
    case (item, spec) =>
      (0 until spec.specify(population)).map(_ => item.copy())
  }
}

object PlaceableConfig {}
