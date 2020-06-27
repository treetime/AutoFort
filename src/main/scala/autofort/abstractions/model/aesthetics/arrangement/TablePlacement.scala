package autofort.abstractions.model.aesthetics.arrangement

import autofort.abstractions.model.aesthetics.arrangement.TablePlacement.{
  TableArrangement,
  TableGrouping
}
import autofort.abstractions.model.aesthetics.preferences.Preference

case class TablePlacement(arrangement: Preference[TableArrangement],
                          grouping: Preference[TableGrouping])

object TablePlacement {

  sealed trait TableGrouping

  case class DiningCluster(blockWidth: Int, doubles: Boolean)
      extends TableGrouping
  case class WorkDesk(minSpacing: Int) extends TableGrouping //aims to maximally fill a wall

  case class Grid(rows: Int, cols: Int, active: Set[Int]) {
    assert(
      active.forall(_ < rows * cols),
      "No active cells outside the grid allowed"
    )
  }

  case class Perimeter(groupSize: Int, thickness: Double) //thickness as fraction of scale

  case class TableArrangement(grid: Grid, perimeter: Perimeter)
}
