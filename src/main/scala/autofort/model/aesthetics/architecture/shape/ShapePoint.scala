package autofort.model.aesthetics.architecture.shape

case class ShapePoint(x: Double, y: Double, curve: Option[Double] = None) { // 0 = straight, -1 = convex, 1 = concave
  def move(dx: Double, dy: Double): ShapePoint = copy(x = x + dx, y = y + dy)
  def isAfter(point: ShapePoint) = x > point.x
  def isAbove(point: ShapePoint) = y > point.y

}
object ShapePoint {
  val ORIGIN: ShapePoint = ShapePoint(0, 0)
}