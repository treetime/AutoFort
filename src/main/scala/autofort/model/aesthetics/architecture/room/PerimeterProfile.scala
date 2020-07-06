package autofort.model.aesthetics.architecture.room

import autofort.model.map.GridBlock

case class PerimeterProfile(internal: Set[GridBlock] = Set.empty,
                            external: Set[GridBlock] = Set.empty,
                            detachable: Set[GridBlock] = Set.empty,
                            lineSpaces: Seq[RectangularArea] = Seq.empty)

object PerimeterProfile {

/*  def fromAreaDefinition(areaDefinition: AreaDefinition): PerimeterProfile = {
    val profile = areaDefinition.perimeter.area
      .groupBy(_.classifyIn(areaDefinition))
      .withDefault(_ => Set.empty[GridBlock])

    PerimeterProfile(
      profile(InternalCorner),
      profile(ExternalCorner),
      profile(Detachable),
      ???
    )
  }*/
}
