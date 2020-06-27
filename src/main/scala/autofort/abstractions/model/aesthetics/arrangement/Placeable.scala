package autofort.abstractions.model.aesthetics.arrangement

import autofort.abstractions.map.GridBlock
import autofort.abstractions.model.aesthetics.arrangement.Placeable.Dimensions

case class Placeable(dim: Dimensions = Dimensions(1,1), location: Set[GridBlock] = Set.empty) { // top left corner?
  def place(l: Set[GridBlock]): Placeable = {
    if (l.size == dim.size) {
      copy(location = l)
    } else {
      throw new RuntimeException(
        "Dwarf Fortress does not currently support stretching items"
      )
    }
  }
}

object Placeable {

  def apply(x: Int, y: Int) = new Placeable(Dimensions(x, y))

  case class Dimensions(x: Int, y: Int) {
    assert(x > 0 && y > 0)
    def size: Int = x * y
  }

}
