package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.shape.{ShapeDefinition, ShapePoint}
import autofort.model.aesthetics.architecture.shape.ShapePointPair.{CUTS_LINE, ON_LINE}
import autofort.model.map.GridBlock

import scala.math.BigDecimal.RoundingMode

case class RoomDefinition(area: AreaDefinition) {

  override def toString: String = area.toString //.withWalls()

  def toString2: String = area.withWalls().toString2 //.withWalls()

  def toString3: String = area.withWalls().toString //.withWalls()

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
    val shapedef = shape.scaled(scale)
    shapedef.pairs
      .foreach({ x =>
        println(s"${x.p1.x}, ${x.p1.y} ");
      })
    RoomDefinition(createAreaDefinition(shapedef))
  }

  private def createAreaDefinition(shape: ShapeDefinition): AreaDefinition = {

    //val pairs = shape.pairs
    //shape.pairs gives rules to determine whether a block is inside or outside the shape
    val validBlocks = blockField(shape).filter { block =>
/*      val (pair, counts) = pairs.map(p => (p, p.intersects(block))).unzip
      val onLine = counts.count(_ == ON_LINE)
      val cutsLine = counts.count(_ == CUTS_LINE)
      (cutsLine != 0 && cutsLine % 2 != 0) || ((onLine % 2 == 0) && (onLine != 0))*/
      shape.isInside(block)
    }
    new AreaDefinition(validBlocks).zeroAt(0, 0)
  }

  def blockField(shape: ShapeDefinition): Set[GridBlock] = {
    def roundAway(d: Double) = BigDecimal(d).setScale(0, RoundingMode.UP).toInt
    (roundAway(shape.yMin) to roundAway(shape.yMax)).flatMap { py =>
      (roundAway(shape.xMin) to roundAway(shape.xMax)).map { px =>
        GridBlock(px, py, 0)
      }
    }.toSet
  }

}
