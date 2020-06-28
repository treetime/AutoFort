package autofort.model.aesthetics.architecture

import autofort.model.aesthetics.architecture.RoomConfig.PlaceableConfig
import autofort.model.aesthetics.architecture.arrangement.ArrangementConfig
import autofort.model.aesthetics.architecture.arrangement.placeables.Placeable

case class RoomConfig(

) { //rules for making rooms

}

object RoomConfig {

  /**
    * Not really a min and max, but it's useful for thinking about how you size your rooms
    */
  private val MIN_DWARVES = 7
  private val MAX_DWARVES = 200
  private val deltaDWARVES = (p: Int) =>
    (MAX_DWARVES - p) / (MAX_DWARVES - MIN_DWARVES)

  trait Specification {
    def specify(population: Int): Int
  }

  trait FloorCeil {
    val floor: Option[Int]
    val ceil: Option[Int]
    def limit(value: Int): Int = {
      val floored = floor.fold(value)(x => Math.max(value, x))
      ceil.fold(floored)(x => Math.min(floored, x))
    }
  }

  case class MinMax(min: Int,
                    max: Int,
                    floor: Option[Int] = None,
                    ceil: Option[Int] = None)
      extends Specification
      with FloorCeil {
    def specify(population: Int): Int = {
      limit(deltaDWARVES(population) * (max - min))
    }
  }

  case class Ratio(min: Int,
                   ratio: Double,
                   floor: Option[Int] = None,
                   ceil: Option[Int] = None)
      extends Specification
      with FloorCeil {
    def specify(population: Int): Int = {
      limit(Math.round(min + ratio * population).toInt)
    }
  }

  case class PlaceableConfig(specs: (Placeable, Specification)*) {
    def generate(population: Int): Seq[Placeable] = specs.flatMap {
      case (item, spec) =>
        (0 until spec.specify(population)).map(_ => item.copy())
    }
  }

}
