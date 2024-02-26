pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "The Smart Calculator"
include(":app")
include(":core:logger")
include(":core:speechmodule")
include(":features:circles")
include(":features:thermometer")
include(":features:primenumbers")
include(":features:speecher")
include(":core:customcomposables")
include(":features:hardmath")
