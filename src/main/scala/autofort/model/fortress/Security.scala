package autofort.model.fortress

import autofort.model.fortress.FortressModel.FortressComponent

trait Security extends FortressComponent

object Security {

  trait OuterWall extends Security //battlements extends wall
  trait Trap extends Security

  trait Barracks extends Security // prison extends barracks
  trait Gate extends Security

  trait WatchTower extends Security

  trait Maze extends Security // passage size, x dimension, z dimension, complexity / connectivity

  trait AccessRemoval extends Security // removes / limits vectors of approach to the main fort

}
