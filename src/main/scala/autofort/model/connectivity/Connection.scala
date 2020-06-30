package autofort.model.connectivity

class Connection(canDeltaXY: Boolean, canDeltaZ: Boolean, canWalk: Boolean)

object Connection {
  case class Corridor() extends Connection(true, false, true)
  case class Shaft() extends Connection(false, true, true)
  case class Ramp() extends Connection(true, true, true)
  case class Stairway() extends Connection(false, true, true)

  class Route(route: Seq[Connection])

  case class Aquaduct(val route: Seq[Connection]) extends Route(route)
}
