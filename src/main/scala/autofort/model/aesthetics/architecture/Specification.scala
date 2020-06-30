package autofort.model.aesthetics.architecture

trait Specification {
  def specify(population: Int): Int
}

object Specification {

  /**
    * Not really a min and max, but it's useful for thinking about how you size your rooms
    */
  private val MIN_DWARVES = 7
  private val MAX_DWARVES = 200
  private val deltaDWARVES = (p: Int) =>
    (MAX_DWARVES - p) / (MAX_DWARVES - MIN_DWARVES)

  trait FloorCeil {
    val floor: Option[Int]
    val ceil: Option[Int]
    def limit(value: Int): Int = {
      val floored = floor.fold(value)(x => Math.max(value, x))
      ceil.fold(floored)(x => Math.min(floored, x))
    }
  }

  case class MinMax(min: Int, max: Int) extends Specification {
    def specify(population: Int): Int = {
      deltaDWARVES(population) * (max - min)
    }
  }

  case class FillPercent(percent: Double) extends Specification {
    def specify(area: Int): Int = {
      Math.round(min + Math.max(ratio * area - 4, 0)).toInt
    }
  }
}
