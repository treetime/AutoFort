package autofort.abstractions.model.aesthetics.architecture

import autofort.abstractions.model.aesthetics.arrangement.TablePlacement
import autofort.abstractions.model.aesthetics.preferences.{Orientation, Preference}

//groups of what? spaced how? xx . xx . vs xxx .. xxx ..

case class ArrangementStrategy(tables: TablePlacement,
                               orientation: Preference[Orientation]) {

}

object ArrangementStrategy {


}
