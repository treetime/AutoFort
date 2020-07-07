package autofort.model.map

import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.materials.Material
import autofort.model.aesthetics.materials.Stone.Granite
import autofort.model.map.WallTile.WallSymbol

case class WallTile(material: Material = Granite(),
                    symbol: WallSymbol = WallSymbol('O')) {
  def withSymbol(wallSymbol: WallSymbol): WallTile =
    copy(symbol = wallSymbol)

  override def toString: String = symbol.toString
}

object WallTile {

  val wallSymbols = Seq(
    (WallSymbol('═'), Set(LEFT, RIGHT)),
    (WallSymbol('║'), Set(UP, DOWN)),
    (WallSymbol('╦'), Set(LEFT, RIGHT, DOWN)),
    (WallSymbol('╩'), Set(LEFT, RIGHT, UP)),
    (WallSymbol('╠'), Set(UP, DOWN, RIGHT)),
    (WallSymbol('╣'), Set(UP, DOWN, LEFT)),
    (WallSymbol('╬'), Set(UP, DOWN, LEFT, RIGHT)),
    (WallSymbol('╔'), Set(DOWN, RIGHT)),
    (WallSymbol('╚'), Set(UP, RIGHT)),
    (WallSymbol('╗'), Set(DOWN, LEFT)),
    (WallSymbol('╝'), Set(UP, LEFT))
  )

  def fromConnections(connections: Set[Direction]): WallTile = {
    WallTile().withSymbol(getSymbol(connections))
  }

  def getSymbol(connections: Set[Direction]): WallSymbol =
    wallSymbols
      .find(_._2 == connections)
      .map(_._1)
      .getOrElse(WallSymbol('O'))

  // walls
  trait Connection

  case class WallSymbol(symbol: Char) {
    override def toString: String = symbol.toString
  }

}
