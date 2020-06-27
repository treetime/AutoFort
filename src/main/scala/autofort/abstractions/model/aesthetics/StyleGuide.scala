package autofort.abstractions.model.aesthetics

import autofort.abstractions.model.aesthetics.personality.Personality

case class StyleGuide(architectureConfig: ArchitectureConfig,
                      decorationConfig: DecorationConfig,
                      materialPreferences: MaterialConfig,
                      personality: Personality) {

}