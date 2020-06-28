package autofort.model.aesthetics

import autofort.model.aesthetics.materials.Material

case class MaterialConfig(primary: Material, secondary: Material, forbidden: Set[Material])