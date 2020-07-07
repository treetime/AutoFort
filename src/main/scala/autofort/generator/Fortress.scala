package autofort.generator

import autofort.model.aesthetics.StyleGuide
import autofort.model.aesthetics.architecture.PreferredShape.nGon
import autofort.model.aesthetics.architecture.room.{
  AreaDefinition,
  RoomDefinition
}
import autofort.model.aesthetics.architecture.{
  ArchitectureConfig,
  ConnectionConfig,
  WithNoConcernForTheFuture
}
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

  val population = 50

  val conf =
    ArchitectureConfig(WithNoConcernForTheFuture(), nGon(8), ConnectionConfig())

  val room = new Shelter().generate(population, conf)

  println(room.toString)
  println(s"${room.area.width + 1} x ${room.area.height + 1} = ${room.area.area}")

/*  val subSpaces = room.area.subSpaces

  subSpaces.rectangularAreas.foreach(x => println(x))*/
/*
  import Math._

  val area = new AreaDefinition((0 to 26).flatMap { py =>
    (0 to 26).flatMap { px =>
      val block = GridBlock(px, py)
      Option
        .when(pow(13, 2) >= pow(block.x - 13, 2) + pow(block.y - 13, 2))(block)
    }
  }.toSet)

  println(RoomDefinition(area).toString)
  println(s"${area.width} x ${area.height} = ${area.area}")
*/

}
