package autofort.model.aesthetics.architecture
import autofort.model.aesthetics.architecture.room.RoomDefinition
import autofort.model.aesthetics.architecture.shape.{ShapeDefinition, ShapePoint}
import autofort.model.map.AreaDefinition

trait PreferredShape {
  val shape: ShapeDefinition
  val scale: Int //side length of the shortest side of the shape in blocks

  def generateArea(scale: Int): AreaDefinition = {
    AreaDefinition.fromShape(shape, scale)
  }
}

object PreferredShape {
  import Math._

  case class Rectangle(scale: Int, ratio: Double) extends PreferredShape {
    assert(ratio != 0, "No lines, brah")
    val shape: ShapeDefinition = ShapeDefinition.fromPoints(
      ShapePoint.ORIGIN,
      ShapePoint(0, 1),
      ShapePoint(1 / ratio, 1),
      ShapePoint(1 / ratio, 0)
    )
  }

  case class nGon(scale: Int, n: Int) extends PreferredShape {
    assert(n >= 3, "No two-angles, onetagons or nonetagons in this dimension")
    val shape: ShapeDefinition = {
      var i = 0
      var angle: Double = 0
      var inc: Double = 2 * Math.PI / n.toDouble //exterior angle
      var shapePoints: Seq[ShapePoint] = Seq(ShapePoint(0, 0))
      while (i < n) {
        shapePoints.lastOption.foreach { prev =>
          angle = angle + inc
          shapePoints = shapePoints :+ ShapePoint(
            prev.x + cos(angle),
            prev.y + sin(angle)
          )
        }
        i = i + 1
      }
      ShapeDefinition(shapePoints.toVector)
    }
  }
}
