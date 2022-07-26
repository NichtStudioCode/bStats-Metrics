import org.gradle.api.credentials.PasswordCredentials

apply(from = "generate-metrics.gradle.kts")
apply(from = "increment-version.gradle.kts")

plugins {
    `java-library`
    `maven-publish`
}

allprojects {
    group = "xyz.xenondevs.bstats"
}

configure<PublishingExtension> {
    subprojects {
        apply(plugin = "java-library")
        apply(plugin = "maven-publish")
        apply(plugin = "signing")
        
        publishing {
            publications {
                create<MavenPublication>("mavenJava") {
                    groupId = "xyz.xenondevs.bstats"
                    afterEvaluate {
                        artifactId = "bstats-${project.name}"
                    }
                    from(components["java"])
                    pom {
                        name.set("bStats-Metrics")
                        description.set("The bStats Metrics class")
                        url.set("https://bStats.org")
                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://opensource.org/licenses/mit-license.php")
                            }
                        }
                        developers {
                            developer {
                                id.set("Bastian")
                                name.set("Bastian Oppermann")
                                email.set("bastian@bstats.org")
                                url.set("https://github.com/Bastian")
                                timezone.set("Europe/Berlin")
                            }
                        }
                    }
                }
            }
            
            repositories {
                maven {
                    name = "xenondevs"
                    url = uri("https://repo.xenondevs.xyz/releases/")
                    credentials(PasswordCredentials::class)
                }
            }
        }
    }
}