package autofort.model.aesthetics.architecture.shape

import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{
  DOWN,
  LEFT,
  RIGHT,
  UP
}

case class ShapeDefinition(shapePoints: IndexedSeq[ShapePoint]) {
  assert(shapePoints.nonEmpty)
  assert(validateShape, "Shape definition incorrect")

  lazy val xMin: Double = shapePoints.map(_.x).max
  lazy val yMin: Double = shapePoints.map(_.y).max
  lazy val xMax: Double = shapePoints.map(_.x).max
  lazy val yMax: Double = shapePoints.map(_.y).max

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

  def pairs: Vector[ShapePointPair] = {
    for {
      last <- shapePoints.lastOption
      first <- shapePoints.headOption
    } yield (shapePoints zip shapePoints.drop(1)) :+ (last, first)
  }.getOrElse(Seq.empty).zipWithIndex.map(ShapePointPair.apply).toVector

  def normalise(): ShapeDefinition = { //normalises smallest dimension to 1
    val yScale = 1.0 / (yMax - yMin)
    val xScale = 1.0 / (xMax - xMin)
    val scalingFactor = Math.max(xScale, yScale)
    transform(scalingFactor)(
      point =>
        point.copy(
          x = (point.x - xMin) * scalingFactor,
          y = (point.y - yMin) * scalingFactor,
      )
    )
  }

  def transform(
    scalingFactor: Double
  )(func: ShapePoint => ShapePoint): ShapeDefinition = {
    copy(shapePoints = shapePoints.map(func))
  }

}

object ShapeDefinition {
  val xScale = 1000
  val yScale = 1000

  def fromPoints(points: ShapePoint*): ShapeDefinition =
    new ShapeDefinition(points.toIndexedSeq)

  sealed trait Direction

  case object UP extends Direction
  case object DOWN extends Direction
  case object LEFT extends Direction
  case object RIGHT extends Direction

}
