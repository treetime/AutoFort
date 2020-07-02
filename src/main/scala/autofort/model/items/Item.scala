package autofort.model.items

class Item(symbol: Char) {

  override def toString: String = symbol.toString

}

object Item {

  case object Throne extends Item('╥')

  case object Table extends Item('╤')

  case object Statue extends Item('Ω')

  case object Bed extends Item('Θ')

  case object Chest extends Item('Æ')

  case object Bookcase extends Item('≡')

  case object Cabinet extends Item('π')

  case object Bag extends Item('Æ')

  case object TractionBench extends Item('?')

  case object WorkshopTile extends Item('#')

  case object StockpileTile extends Item('=')

}
