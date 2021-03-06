package autofort.model.aesthetics.architecture.shape

import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{DOWN, LEFT, RIGHT, UP}
import autofort.model.aesthetics.architecture.shape.ShapePointPair.CUTS_LINE
import autofort.model.map.GridBlock

import scala.math.BigDecimal.RoundingMode

case class ShapeDefinition(shapePoints: IndexedSeq[ShapePoint]) {
  assert(shapePoints.nonEmpty)
//  assert(validateShape, "Shape definition incorrect")

  lazy val xMin: Double = shapePoints.map(_.x).minOption.getOrElse(0.0)
  lazy val yMin: Double = shapePoints.map(_.y).minOption.getOrElse(0.0)
  lazy val xMax: Double = shapePoints.map(_.x).maxOption.getOrElse(0.0)
  lazy val yMax: Double = shapePoints.map(_.y).maxOption.getOrElse(0.0)

  lazy val width: Double = xMax - xMin
  lazy val height: Double = yMax - yMin

  lazy val pairs: Vector[ShapePointPair] = {
    for {
      last <- shapePoints.lastOption
      first <- shapePoints.headOption
    } yield (shapePoints zip shapePoints.drop(1)) :+ (last, first)
  }.getOrElse(Seq.empty).zipWithIndex.map(ShapePointPair.apply).toVector

  def validateShape: Boolean = { // only allow closed shapes
    val grouped = pairs
      .map(_.direction)
      .groupBy(identity)
      .map {
        case (dir, seq) =>
          (dir, seq.length)
      }
    grouped(UP) == grouped(DOWN) &&
    grouped(LEFT) == grouped(RIGHT)
  }

  def normalise(): ShapeDefinition = { //normalises smallest dimension to 1
    val yScale = 1.0 / (yMax - yMin)
    val xScale = 1.0 / (xMax - xMin)
    val scalingFactor = Math.max(xScale, yScale)
    transform(
      point =>
        point.copy(
          x = (point.x - xMin) * scalingFactor,
          y = (point.y - yMin) * scalingFactor,
      )
    )
  }

  def transform(func: ShapePoint => ShapePoint): ShapeDefinition = {
    copy(shapePoints = shapePoints.map(func))
  }

  def transformAndZero(scale: Double): ShapeDefinition = {
    val scaledArea = scaled(scale)
    scaledArea.zero()
  }

  def scaled(scale: Double): ShapeDefinition =
    copy(
      shapePoints.map(
        p => p.copy(x = roundAway(p.x * scale), y = roundAway(p.y * scale))
      )
    )

  private def roundAway(d: Double) =
    BigDecimal(d).setScale(1, RoundingMode.UP).toDouble

  def zero(): ShapeDefinition = transform(p => p.move(-xMin, -yMin))

  def isInside(block: GridBlock): Boolean = {
    val results = pairs.map(_.intersect(block))
      results.flatten.count(_ == CUTS_LINE) % 2 != 0
  }


}

object ShapeDefinition {
  val xScale = 1000
  val yScale = 1000

  def fromPoints(points: ShapePoint*): ShapeDefinition = {
    val xMin = points.map(_.x).minOption.getOrElse(0d)
    val xMax = points.map(_.x).maxOption.getOrElse(0d)
    val yMin = points.map(_.y).minOption.getOrElse(0d)
    val yMax = points.map(_.y).maxOption.getOrElse(0d)

    val xSize = xMax - xMin
    val ySize = yMax - yMin

    val impliedArea = xSize * ySize

    val ratio = Math.sqrt(1.0 / impliedArea)

    val shape = new ShapeDefinition(
      points.toIndexedSeq.toIndexedSeq
        .map(_.move(-xMin - xSize / 2.0, -yMin - ySize / 2.0))
        .map(p => p.copy(x = ratio * p.x, y = ratio * p.y))
    )

    shape
  }

  private def apply(shapePoints: IndexedSeq[ShapePoint]) =
    new ShapeDefinition(shapePoints)

  sealed trait Direction

  case object UP extends Direction
  case object DOWN extends Direction
  case object LEFT extends Direction
  case object RIGHT extends Direction

}
