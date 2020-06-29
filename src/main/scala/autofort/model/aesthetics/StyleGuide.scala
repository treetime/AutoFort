package autofort.model.aesthetics

import autofort.model.aesthetics.architecture.ArchitectureConfig
import autofort.model.aesthetics.decorations.DecorationConfig
import autofort.model.aesthetics.materials.MaterialConfig
import autofort.model.aesthetics.personality.Personality

case class StyleGuide(architectureConfig: ArchitectureConfig,
                      decorationConfig: DecorationConfig,
                      materialPreferences: MaterialConfig,
                      personality: Personality) {

}