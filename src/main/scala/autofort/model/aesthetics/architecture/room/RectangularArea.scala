package autofort.model.aesthetics.architecture.room

import autofort.model.map.GridBlock

case class RectangularArea(override val blocks: Set[GridBlock])
    extends AreaDefinition(blocks) {

  def scaleTo(fx: Double, fy: Double = 1): RectangularArea = {
    val dx = Math.round((width * (1 - fx)) / 2.0).toInt
    val dy = Math.round((height * (1 - fy)) / 2.0).toInt
    shrink(dx, dy)
  }

  def shrink(dx: Int, dy: Int = 0): RectangularArea = {
    copy(blocks = blocks.filter { block =>
      block.x >= dx && block.x < width - dx &&
      block.x >= dy && block.y < height - dy
    })
  }

  override def move(dx: Int, dy: Int): RectangularArea =
    copy(blocks.map(_.move(dx, dy)))

  def subsetOf(area: AreaDefinition): Boolean = {
    area.containsArea(this)
  }



}

object RectangularArea {

  def apply(x: Int, y: Int): RectangularArea = RectangularArea(
    (0 until y).flatMap { py =>
      (0 until x).map { px =>
        GridBlock(px, py, 0)
      }
    }.toSet
  )

}
