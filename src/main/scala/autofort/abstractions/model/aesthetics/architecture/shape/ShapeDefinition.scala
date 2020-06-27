package autofort.abstractions.model.aesthetics.architecture.shape

case class ShapeDefinition(shapePoints: IndexedSeq[ShapePoint]) {
  assert(shapePoints.nonEmpty)
  assert(validateShape, "Shape definition incorrect")

  def asConditions(scale: Int): Vector[Condition] = pairs.map(_.function(scale))

  def pairs: Vector[ShapePointPair] = {
    for {
      last <- shapePoints.lastOption
      first <- shapePoints.headOption
    } yield (shapePoints zip shapePoints.drop(1)) :+ (last, first)
  }.getOrElse(Seq.empty).zipWithIndex.map(ShapePointPair.apply).toVector

  def xMax(scale: Int): Int = (shapePoints.map(_.x).max * scale).toInt

  def yMax(scale: Int): Int = (shapePoints.map(_.y).max * scale).toInt

  def validateShape: Boolean = {
    import autofort.abstractions.model.aesthetics.architecture.shape.Condition._
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
}

object ShapeDefinition {
  val xScale = 1000
  val yScale = 1000

  def apply(points: ShapePoint*) = new ShapeDefinition(points.toIndexedSeq)

}
