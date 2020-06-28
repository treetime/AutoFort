package autofort.model.map

import GridMap._
import autofort.model.map.AreaMap.XYMap
import autofort.model.aesthetics.materials.Material
import autofort.model.fortress.arrangement.placeables.Placeable

case class GridMap(levels: Vector[GridLevel]) extends AreaMap[GridBlock](levels)

object GridMap {

  /**
    * The fortress lies horizontally in memory, with the top of the world at index 0, and the bottom at the max index.
    * We expect to spend more time at the top of the world, so need to have it close.
    */
  def apply(x: Int, y: Int, z: Int) = new GridMap(
    (z to 0)
      .map(
        pz =>
          new GridLevel(
            (0 until x)
              .map(
                px =>
                  (0 until y)
                    .map(py => GridBlock(px, py, pz))
                    .toVector
              )
              .toVector
        )
      )
      .toVector
  )

  trait XYDimensions {
    val x, y: Int
    def touches(other: XYDimensions): Boolean = {
      Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1
    }

    //def moveX: XYDimensions
  //  def moveY: XYDimensions

  }

  case class GridLevel(override val xy: Vector[Vector[GridBlock]])
      extends XYMap[GridBlock](xy) {

    override def get(x: Int, y: Int): Option[GridBlock] =
      for {
        row <- xy.find(p => p.headOption.exists(_.location.x == x))
        cell <- row.find(g => g.location.y == y)
      } yield cell

    //second argument is the incoming level
    def applyFunction(
      level: GridLevel
    )(func: (GridBlock, GridBlock) => GridBlock): GridLevel = {
      GridLevel(cellZip(level).map { row =>
        row.map {
          case (current, incoming) =>
            func(current, incoming)
        }
      })
    }

    private def cellZip(
      level: GridLevel
    ): Vector[Vector[(GridBlock, GridBlock)]] =
      xy.zip(level.xy).map { case (a, b) => a zip b }
  }

}