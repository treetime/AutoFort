package autofort.model.placeables

import autofort.model.placeables.Placeable.Dimensions

case class Placeable(symbol: Char)

object Placeable {

  case class Dimensions(x: Int, y: Int) {
    assert(x > 0 && y > 0)
    def size: Int = x * y
  }

  def Throne() = Placeable('╥')
  def Table() = Placeable('╤')
  def Statue() = Placeable('Ω')
  def Bed() = Placeable('Θ')
  def Chest() = Placeable('Æ')
  def Bookcase() = Placeable('≡')
  def Cabinet() = Placeable('π')
  def Bag() = Placeable('Æ')
  def TractionBench() = Placeable('?')



}
