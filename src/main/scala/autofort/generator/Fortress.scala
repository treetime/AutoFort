package autofort.generator

import autofort.model.aesthetics.StyleGuide
import autofort.model.aesthetics.architecture.PreferredShape.nGon
import autofort.model.aesthetics.architecture.room.AreaDefinition
import autofort.model.aesthetics.architecture.{ArchitectureConfig, ConnectionConfig, WithNoConcernForTheFuture}
import autofort.model.embark.Embark
import autofort.model.fortress.{FortressModel, Shelter}
import autofort.model.map.{GridBlock, GridMap}

class Fortress(embark: Embark,
               grid: GridMap,
               model: FortressModel,
               style: StyleGuide) {
  val population: Int = 0

}

object Fortress extends App {

  val population = 150

  val conf =
    ArchitectureConfig(WithNoConcernForTheFuture(), nGon(3), ConnectionConfig())

  val room = new Shelter().generate(population, conf)

  println(room.toString)
  println(
    s"${room.area.width} x ${room.area.height} = ${room.area.area}"
  )
/*import Math._
  val area = new AreaDefinition(
  (0 until 26).flatMap{py =>
    (0 until 26).map{px =>
     val block = GridBlock(px, py)
     if(pow(12,2) < pow(block.x - 12,2) + pow(block.y - 12,2)) block else block.withFloorTile(None)
    }
  }.toSet
  )

  println(area.toString)*/

}
