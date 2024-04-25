plugins {
    id("com.specificlanguages.mps") version "1.2.2"
    `maven-publish`
}

repositories {
    maven(url = "https://projects.itemis.de/nexus/content/repositories/mbeddr")
    mavenCentral()
}

dependencies {
    "mps"("com.jetbrains:mps:2023.2")
}

group = "com.specificlanguages"
version = "1.1.0"

publishing {
    publications {
        register<MavenPublication>("mpsPlugin") {
            from(components["mps"])

            // Put resolved versions of dependencies into POM files -- uncomment as soon as we have any dependencies
            // versionMapping { usage("java-runtime") { fromResolutionOf("generation") } }
        }
    }
    repositories {
        if (project.hasProperty("gpr.user")) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/specificlanguages/mps-json")
                credentials {
                    username = project.findProperty("gpr.user") as String?
                    password = project.findProperty("gpr.key") as String?
                }
            }
        }
    }
}
