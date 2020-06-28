package autofort.model.fortress

import autofort.model.fortress.FortressModel.FortressComponent

trait SleepingArea extends FortressComponent

object SleepingArea {

  trait Room extends SleepingArea
  trait Dormitory extends SleepingArea

}
