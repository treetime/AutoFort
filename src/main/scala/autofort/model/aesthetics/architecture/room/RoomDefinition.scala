package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.shape.ShapeDefinition
import autofort.model.aesthetics.architecture.shape.ShapePointPair.{
  CUTS_LINE,
  ON_LINE
}
import autofort.model.map.GridBlock

case class RoomDefinition(area: AreaDefinition) {

  override def toString: String = area.toString

}

object RoomDefinition {

  /**
    * Creates a room where the shortest side of the largest rectangle in the room is "scale"
    */
  def fromShape(shape: ShapeDefinition, scale: Int): RoomDefinition = {
/*    val bigScale = 10
    val reference = createAreaDefinition(shape, bigScale)
    val areaScale = scale * Math.sqrt(
      reference.subSpaces.rectangularAreas
        .maxBy(_.area)
        .area
        .toDouble
    ) / bigScale.toDouble*/
    RoomDefinition(createAreaDefinition(shape, 25))
  }

  private def createAreaDefinition(shape: ShapeDefinition,
                                   scale: Int): AreaDefinition = {
    val xMax = Math.round(shape.width * scale).toInt
    val yMax = Math.round(shape.height * scale).toInt
    val pairs = shape.pairs.map(_.scaled(scale))
    //shape.pairs gives rules to determine whether a block is inside or outside the shape
    val validBlocks = blockField(xMax, yMax).filter { block =>
      val (pair,counts) = pairs.map(p => (p, p.intersects(block))).unzip
      val onLine = counts.count(_ == ON_LINE)
      val cutsLine = counts.count(_ == CUTS_LINE)
      cutsLine % 2 != 0 || (onLine % 2 == 0 && onLine != 0)
    }
    new AreaDefinition(validBlocks)
  }

  def blockField(xMax: Int, yMax: Int): Set[GridBlock] = {
    (0 to yMax).flatMap { py =>
      (0 to xMax).map { px =>
        GridBlock(px, py, 0)
      }
    }.toSet
  }

}
