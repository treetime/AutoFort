package autofort.model.aesthetics.materials

trait Metal extends Material

object Metal {

  case class Copper() extends Metal
  case class Iron() extends Metal
  case class Steel() extends Metal
  case class Zinc() extends Metal
  case class Lead() extends Metal
  case class Silver() extends Metal

}
