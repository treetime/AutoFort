package autofort.model.aesthetics.architecture

trait RoomNeed

case class Dining() extends RoomNeed
case class Study() extends RoomNeed
//case class Grates() extends RoomNeed //needs to be able to say "channel me"
case class Door() extends RoomNeed
case class Gate() extends RoomNeed

case class Workshop[T]() extends RoomNeed

case class Stockpile[T]() extends RoomNeed


case class RoomNeedConfig(need: RoomNeed, quantity: Int)
