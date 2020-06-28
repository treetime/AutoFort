package autofort.model.aesthetics

import autofort.model.aesthetics.personality.Personality

case class StyleGuide(architectureConfig: ArchitectureConfig,
                      decorationConfig: DecorationConfig,
                      materialPreferences: MaterialConfig,
                      personality: Personality) {

}