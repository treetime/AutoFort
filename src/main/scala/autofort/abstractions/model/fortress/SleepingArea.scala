package autofort.abstractions.model.fortress

import autofort.abstractions.model.fortress.FortressModel.FortressComponent

trait SleepingArea extends FortressComponent

object SleepingArea {

  trait Room extends SleepingArea
  trait Dormitory extends SleepingArea

}
