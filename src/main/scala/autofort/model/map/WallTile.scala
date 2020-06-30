package autofort.model.map

import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.materials.Material
import autofort.model.map.WallTile.WallSymbol

case class WallTile(material: Material, symbol: Option[WallSymbol])

object WallTile {
  val wallSymbols = Seq(
    WallSymbol('═', Set(LEFT, RIGHT)),
    WallSymbol('║', Set(UP, DOWN)),
    WallSymbol('╦', Set(LEFT, RIGHT, DOWN)),
    WallSymbol('╩', Set(LEFT, RIGHT, UP)),
    WallSymbol('╠', Set(UP, DOWN, RIGHT)),
    WallSymbol('╣', Set(UP, DOWN, LEFT)),
    WallSymbol('╬', Set(UP, DOWN, LEFT, RIGHT)),
    WallSymbol('╔', Set(UP, RIGHT)),
    WallSymbol('╚', Set(DOWN, RIGHT)),
    WallSymbol('╗', Set(DOWN, LEFT)),
    WallSymbol('╝', Set(UP, LEFT))
  )

  def getSymbol(connections: Set[Direction]): WallSymbol =
    wallSymbols
      .find(_.connections == connections)
      .getOrElse(unconnected(connections))

  private def unconnected(connections: Set[Direction]): WallSymbol =
    WallSymbol('O', connections)

  // walls
  trait Connection

  case class Point(x: Int, y: Int, z: Int) {
    def move(dx: Int, dy: Int, dz: Int): Point =
      copy(x = x + dx, y = y + dy, z = z + dz)

    def transpose: Point = copy(x = y, y = x)
  }

  case class WallSymbol(symbol: Char, connections: Set[Direction])

}
