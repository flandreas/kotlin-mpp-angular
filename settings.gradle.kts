pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

rootProject.name = "techstack"

include("domain")
include("ui")

enableFeaturePreview("GRADLE_METADATA")