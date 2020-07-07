package autofort.model.aesthetics.architecture.room

import autofort.model.map.GridBlock

case class SubSpaces(rectangularAreas: Seq[RectangularArea])

object SubSpaces {

  def fromArea(areaDefinition: AreaDefinition): SubSpaces = SubSpaces(
    getRectangles(areaDefinition)
  )

  def getRectangles(areaDefinition: AreaDefinition): Seq[RectangularArea] = {
    @scala.annotation.tailrec
    def removeLargestRectangle(
      area: AreaDefinition,
      acc: Seq[RectangularArea]
    ): Seq[RectangularArea] = {
      val largest = getLargestRectangle(area)
      val newArea = area - largest
      if (newArea.blocks.isEmpty || largest.blocks.isEmpty) {
        acc :+ largest
      } else {
        removeLargestRectangle(newArea, acc :+ largest)
      }
    }
    val moved = areaDefinition.move(-areaDefinition.xMin, -areaDefinition.yMin)
    val largest = removeLargestRectangle(moved, Seq.empty)
    largest.map(_.move(areaDefinition.xMin, areaDefinition.yMin))
  }

  def getLargestRectangle(area: AreaDefinition): RectangularArea = {
    @scala.annotation.tailrec
    def findForCoordinate(
      x: Int,
      y: Int,
      w: Int = 1,
      h: Int = 1,
      largest: RectangularArea = RectangularArea(0, 0)
    ): RectangularArea = {
      if (area.areaContains(GridBlock(x + w - 1, y + h - 1))) {
        val thisArea = RectangularArea(w, h).move(x, y)
        val newLargest =
          if (thisArea.subsetOf(area))
            Seq(largest, thisArea).maxBy(_.area)
          else largest
        findForCoordinate(x, y, w + 1, h, newLargest)
      } else if (y + h > area.height) {
        largest
      } else {
        findForCoordinate(x, y, 1, h + 1, largest)
      }
    }
    val mapped = area.blocks.toVector.map(b => findForCoordinate(b.x, b.y))
    val max = mapped.groupBy(_.area).maxBy(_._1)._2.minBy(_.leftTop.r)
    max
    /*    area
        .move(-area.xMin, -area.yMin)
      .blocks.toVector
      .map(b => findForCoordinate(b.x, b.y))
      .maxBy(_.area)
      .move(area.xMin, area.yMin)*/

  }

}
