rootProject.name = "sample"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("local") {
            from(files("gradle/local.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
    }
}
