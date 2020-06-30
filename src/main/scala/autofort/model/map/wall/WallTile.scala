package autofort.model.map.wall

import autofort.model.aesthetics.materials.Material
import autofort.model.map.wall.WallTile.WallSymbol

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

  def getSymbol(connections: Set[Connection]): WallSymbol =
    wallSymbols
      .find(_.connections == connections)
      .getOrElse(unconnected(connections))

  private def unconnected(connections: Set[Connection]): WallSymbol =
    WallSymbol('O', connections)

  // walls
  trait Connection

  case class Point(x: Int, y: Int, z: Int) {
    def move(dx: Int, dy: Int, dz: Int): Point =
      copy(x = x + dx, y = y + dy, z = z + dz)
  }

  case class WallSymbol(symbol: Char, connections: Set[Connection])

  case object LEFT extends Connection

  case object RIGHT extends Connection

  case object UP extends Connection

  case object DOWN extends Connection
}
