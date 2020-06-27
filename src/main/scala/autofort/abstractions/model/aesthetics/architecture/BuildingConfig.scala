package autofort.abstractions.model.aesthetics.architecture

case class Foundations(oversize: Int, height: Int, depth: Int)

case class BuildingConfig(preferredGeometry: PreferredGeometry, foundations: Foundations) //rules for making above-ground buildings