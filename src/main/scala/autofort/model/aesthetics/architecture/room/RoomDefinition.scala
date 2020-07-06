package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.shape.ShapeDefinition
import autofort.model.map.GridBlock

case class RoomDefinition(area: AreaDefinition) {

  override def toString: String = area.toString

}

object RoomDefinition {

  /**
    * Creates a room where the shortest side of the largest rectangle in the room is "scale"
    */
  def fromShape(shape: ShapeDefinition, scale: Int): RoomDefinition = {
    // val bigScale = 30
    // val reference = createSubspaces(shape, bigScale)
    // val areaScale = scale * reference.rectangularAreas
    //   .maxBy(_.area)
    //  .minDimension / bigScale.toDouble
    RoomDefinition(createAreaDefinition(shape, scale))
  }

  private def createAreaDefinition(shape: ShapeDefinition,
                                   scale: Int): AreaDefinition = {
    val xMax = Math.round(shape.xMax * scale).toInt
    val yMax = Math.round(shape.yMax * scale).toInt
    //shape.pairs gives rules to determine whether a block is inside or outside the shape
    val validBlocks = blockField(xMax, yMax).filter { block =>
      shape.pairs.exists(_.pairContains(block, scale))
    }
    new AreaDefinition(validBlocks)
  }

  def blockField(xMax: Int, yMax: Int): Set[GridBlock] = {
    (0 until yMax).flatMap { py =>
      (0 until xMax).map { px =>
        GridBlock(px, py, 0)
      }
    }.toSet
  }

}
