plugins {
    id("com.specificlanguages.mps") version "1.8.0"
    `maven-publish`
    signing
    id("com.gradleup.nmcp").version("0.0.8")
}

repositories {
    maven { url = uri("https://artifacts.itemis.cloud/repository/maven-mps/") }
    mavenCentral()
}

dependencies {
    "mps"("com.jetbrains:mps:2023.2.1")
}

group = "com.specificlanguages.mps-json"
version = "2.0.0"

// Empty jar for fulfilling Maven Central requirements
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier = "sources"
}

// Empty jar for fulfilling Maven Central requirements
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier = "javadoc"
}

publishing {
    publications {
        register<MavenPublication>("mpsPlugin") {
            from(components["mps"])
            artifact(sourcesJar)
            artifact(javadocJar)

            // Put resolved versions of dependencies into POM files -- uncomment as soon as we have any dependencies
            // versionMapping { usage("java-runtime") { fromResolutionOf("generation") } }

            pom {
                val repo = "specificlanguages/mps-json"
                name = "${project.group}:${project.name}"
                description = "An implementation of JSON language for MPS"
                url = "https://github.com/$repo"

                scm {
                    connection = "scm:git:git://github.com/$repo.git"
                    developerConnection = "scm:git:ssh://github.com:$repo.git"
                    url = "https://github.com/$repo"
                }

                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                developers {
                    developer {
                        name = "Sergej Koščejev"
                        email = "sergej@koscejev.cz"
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
    setRequired({ gradle.taskGraph.hasTask("publish") })
}

nmcp {
    // nameOfYourPublication must point to an existing publication
    publish("mpsPlugin") {
        username = providers.gradleProperty("sonatypeCentralUsername")
        password = providers.gradleProperty("sonatypeCentralPassword")
        publicationType = "USER_MANAGED"
    }
}
