package autofort.model.aesthetics.architecture.shape

case class ShapePoint(x: Double, y: Double, curve: Option[Double] = None) // 0 = straight, -1 = convex, 1 = concave

object ShapePoint {
  case object ORIGIN extends ShapePoint(0, 0)
}