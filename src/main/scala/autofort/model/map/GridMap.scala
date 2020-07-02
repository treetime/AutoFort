package autofort.model.map

import autofort.model.map.GridMap._

case class GridMap(levels: Vector[GridLevel])

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

  case class GridLevel(xy: Vector[Vector[GridBlock]]) {

    def get(x: Int, y: Int): Option[GridBlock] =
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
