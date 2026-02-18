object Constants {
    const val MOD_VERSION = "0.2.5"
}

plugins {
    alias(libs.plugins.fabric.loom)
}

base {
    group = "io.github.startsmercury.luminous_no_shading"
    archivesName = "luminous-no-shading"
    version = createVersionString()
}

java {
    withSourcesJar()

    toolchain {
        languageVersion = libs.versions.java.map(JavaLanguageVersion::of)
    }
}

loom {
    accessWidenerPath = file("src/client/resources/luminous-no-shading.accesswidener")
    runtimeOnlyLog4j = true
    splitEnvironmentSourceSets()

    mods.register("luminous-no-shading") {
        sourceSet("main")
        sourceSet("client")
    }
}

repositories {

}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
}

tasks {
    val validateMixinName by registering(net.fabricmc.loom.task.ValidateMixinNameTask::class) {
        source(sourceSets.main.get().output)
        source(sourceSets.named("client").get().output)
    }

    withType<ProcessResources> {
        val data = mapOf(
            "version" to Constants.MOD_VERSION,
            "version_java" to libs.versions.java.get(),
            "version_game" to libs.versions.fabric.minecraft.get(),
            "version_minecraft" to libs.versions.minecraft.get(),
        )

        inputs.properties(data)

        filesMatching("fabric.mod.json") {
            expand(data)
        }
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

/******************************************************************************/
/* COMPATIBILITY TESTS                                                        */
/******************************************************************************/

repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "CaffeineMC Maven"
                url = uri("https://maven.caffeinemc.net/releases")
                mavenContent {
                    releasesOnly()
                }
            }
        }

        forRepository {
            maven {
                name = "CaffeineMC Snapshots Maven"
                url = uri("https://maven.caffeinemc.net/snapshots")
                mavenContent {
                    snapshotsOnly()
                }
            }
        }

        filter {
            includeGroup("net.caffeinemc")
        }
    }

    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth Maven"
                url = uri("https://api.modrinth.com/maven")
            }
        }

        filter {
            includeGroup("maven.modrinth")
        }
    }
}

run {
    createCompatTest("iris", libs.iris, libs.sodium.fabric)
    createCompatTest("sodium", libs.sodium.fabric)
}

dependencies {
    modCompileOnly(libs.sodium.api)
}

/******************************************************************************/
/* HELPER FUNCTIONS                                                           */
/******************************************************************************/

fun createCompatTest(name: String, objectNotation: Any, vararg dependencyNotations: Any) {
    val config = configurations.register(name)
    val configClasspath = configurations.register("${name}Classpath") {
        extendsFrom(config.get())
    }
    configurations {
        val modCompileOnly by getting {
            extendsFrom(config.get())
        }
    }

    dependencies {
        add(name, objectNotation)
        dependencyNotations.forEach {
            add("${name}Classpath", it)
        }
    }

    afterEvaluate {
        loom.runs.register(name) {
            client()

            property("fabric.addMods", configClasspath.get().files.joinToString(File.pathSeparator))
        }
    }
}

fun createVersionString(): String {
    val builder = StringBuilder()

    val isReleaseBuild = project.hasProperty("build.release")
    val buildId = System.getenv("GITHUB_RUN_NUMBER")

    if (isReleaseBuild) {
        builder.append(Constants.MOD_VERSION)
    } else {
        builder.append(Constants.MOD_VERSION.substringBefore('-'))
        builder.append("-snapshot")
    }

    builder.append("+mc").append(libs.versions.minecraft.get())

    if (!isReleaseBuild) {
        if (buildId != null) {
            builder.append("-build.${buildId}")
        } else {
            builder.append("-local")
        }
    }

    return builder.toString()
}
