package autofort.model.placeables

case class Placeable(symbol: Char) {

  override def toString: String = symbol.toString

}

object Placeable {

  //class Stockpile(x: Int, y: Int) extends Placeable('=')

  case class Dimensions(x: Int, y: Int) {
    assert(x > 0 && y > 0)
    def size: Int = x * y
  }

  case object Throne extends Placeable('╥')

  case object Table extends Placeable('╤')

  case object Statue extends Placeable('Ω')

  case object Bed extends Placeable('Θ')

  case object Chest extends Placeable('Æ')

  case object Bookcase extends Placeable('≡')

  case object Cabinet extends Placeable('π')

  case object Bag extends Placeable('Æ')

  case object TractionBench extends Placeable('?')

  case object Workshop extends Placeable('#')

  case object Stockpile extends Placeable('=')

}
