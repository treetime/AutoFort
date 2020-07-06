package autofort.model.aesthetics.architecture
import autofort.model.aesthetics.architecture.room.RoomDefinition
import autofort.model.aesthetics.architecture.shape.{
  ShapeDefinition,
  ShapePoint
}

trait PreferredShape {
  val shape: ShapeDefinition

  def generateArea(scale: Int): RoomDefinition = {
    RoomDefinition.fromShape(shape, scale)
  }
}

object PreferredShape {
  import Math._

  case class Rectangle(ratio: Double = 1) extends PreferredShape {
    assert(ratio != 0, "No lines, brah")
    val shape: ShapeDefinition = ShapeDefinition.fromPoints(
      ShapePoint(0, 0),
      ShapePoint(0, ratio),
      ShapePoint(1, ratio),
      ShapePoint(1, 0)
    )
  }

  case class nGon(n: Int) extends PreferredShape {
    assert(n >= 3, "No two-angles, onetagons or nonetagons in this dimension")
    /*private val inc: Double = 2 * Math.PI / n.toDouble
    private val offs: Double = - Math.PI / 2

    val shape: ShapeDefinition = ShapeDefinition.fromPoints(
      (0 until n).map(
        i =>
          ShapePoint(
            Math.round(10 * cos(offs + i * inc)) / 10.0,
            Math.round(10 * sin(offs + i * inc)) / 10.0
        )
      ): _*
    )*/

        val shape: ShapeDefinition = {
      var i = 0
      var angle: Double = 0
      var inc: Double = 2 * Math.PI / n.toDouble //exterior angle
      var shapePoints: Seq[ShapePoint] = Seq(ShapePoint(0, 0))
      while (i < n - 1) {
        shapePoints.lastOption.foreach { prev =>
          angle = angle + inc
          shapePoints = shapePoints :+ ShapePoint(
            round((prev.x + cos(angle)) * 10.0) / 10.0,
            round((prev.y + sin(angle)) * 10.0) / 10.0,
          )
        }
        i = i + 1
      }
      ShapeDefinition.fromPoints(shapePoints:_*)
    }
  }
}
