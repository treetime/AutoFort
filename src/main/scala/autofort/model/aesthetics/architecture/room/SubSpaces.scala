package autofort.model.aesthetics.architecture.room

case class SubSpaces(rectangularAreas: Seq[RectangularArea],
                     perimeterProfile: PerimeterProfile = PerimeterProfile())

object SubSpaces {

  def fromArea(areaDefinition: AreaDefinition): SubSpaces = SubSpaces(
    getRectangles(areaDefinition)
  )

  def getRectangles(area: AreaDefinition): Seq[RectangularArea] = {
    @scala.annotation.tailrec
    def findForCoordinate(
      x: Int,
      y: Int,
      w: Int = 1,
      h: Int = 1,
      largest: RectangularArea = RectangularArea(0, 0)
    ): RectangularArea = {
      if (y + h > area.height) {
        largest
      } else if (x + w > area.width) {
        findForCoordinate(x, y, 1, h + 1, largest)
      } else {
        val thisArea = RectangularArea(w, h)
        val newLargest =
          if (thisArea.move(x, y).subsetOf(area))
            Seq(largest, thisArea).maxBy(_.area)
          else largest
        findForCoordinate(x, y, w + 1, h, newLargest)
      }
    }

    area.blocks.toVector
      .sortBy(_.y)
      .sortBy(_.x)
      .map(b => findForCoordinate(b.x, b.y))
      .sortBy(_.area)
      .reverse
      .foldLeft(Seq.empty[RectangularArea]) {
        case (acc, area) =>
          if (acc.exists(_.blocks.intersect(area.blocks).nonEmpty)) {
            acc
          } else {
            acc :+ area
          }
      }

  }

}
