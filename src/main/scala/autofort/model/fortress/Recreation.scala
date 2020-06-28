package autofort.model.fortress

import autofort.model.fortress.FortressModel.FortressComponent

trait Recreation extends FortressComponent

object Recreation {

  //property of embark: distribution of recreation points. religious cult prefers temples
  trait Tavern extends Recreation
  trait Temple extends Recreation
  trait Library extends Recreation
  trait GuildHall extends Recreation // appears in cities with production Reasons

}
