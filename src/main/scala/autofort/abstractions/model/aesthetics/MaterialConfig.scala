package autofort.abstractions.model.aesthetics

import autofort.abstractions.model.aesthetics.materials.Material

case class MaterialConfig(primary: Material, secondary: Material, forbidden: Set[Material])