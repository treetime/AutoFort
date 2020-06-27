package autofort.abstractions.model.aesthetics.materials

trait Wood extends Material

object Wood {

  case class Willow() extends Wood
  case class Fungiwood() extends Wood
  case class NetherCap() extends Wood
  case class FeatherWood() extends Wood
  case class Glumprong() extends Wood

}
