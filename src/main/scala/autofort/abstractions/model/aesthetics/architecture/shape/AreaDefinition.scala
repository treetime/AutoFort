package autofort.abstractions.model.aesthetics.architecture.shape

import autofort.abstractions.map.GridBlock

case class AreaDefinition(area: Vector[GridBlock]) {

  val xSize: Int = {
    val xs = area.map(_.x)
    xs.max - xs.min
  }

  val ySize: Int = {
    val ys = area.map(_.y)
    ys.max - ys.min
  }

  val maxDimension: Int = if(xSize > ySize) xSize else ySize

  val (center, perimeter) = area.partition { gridBlock =>
    area.count(b => b.touches(gridBlock)) == 8
  } match {
    case (cent, peri) => (AreaDefinition(cent), AreaDefinition(peri))
  }

  def largestSquare: Int = {
    (0 until maxDimension).map(countSquares).zipWithIndex.maxBy(_._1)._2
  }

  def countSquares(sideLength: Int): Int = {
    area.count { block: GridBlock =>
      (0 until sideLength).forall { h =>
        (0 until sideLength).forall { v =>
          area.exists { b =>
            b.x == block.x + h &&
            b.y == block.y + v
          }
        }
      }
    }
  }

  def profileArea: Double = {
    val range = (0 until maxDimension)
    range.map(countSquares).sum.toDouble / range
      .map(theoreticalMax)
      .sum
      .toDouble
  }

  private def theoreticalMax(sideLength: Int): Int = {
    val xMax = xSize - sideLength
    val yMax = ySize - sideLength
    if (xMax > 0 && yMax > 0) xMax * yMax else 0
  }

}

object AreaDefinition {

  def fromShape(shape: ShapeDefinition, scale: Int): AreaDefinition = {
    val xMax = shape.xMax(scale)
    val yMax = shape.yMax(scale)
    val conditions = shape.asConditions(scale) //gives rules to determine whether a block is inside or outside the shape
    val validBlocks = blockField(xMax, yMax).filter { block =>
      conditions.forall(_.holds(block.x, block.y))
    }
    AreaDefinition(validBlocks)
  }

  def blockField(xMax: Int, yMax: Int): Vector[GridBlock] = {
    (0 until xMax).flatMap { px =>
      (0 until yMax).map { py =>
        GridBlock(px, py)
      }
    }.toVector
  }

}
