package autofort.model.map

object Zones {
  case object Surface
  case object Cavern1
  case object Cavern2
  case object Cavern3
  case object MagmaSea

  trait SecurityZone
  case object Inside extends SecurityZone
  case object Outside extends SecurityZone
}
