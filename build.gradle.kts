object Constants {
    const val VERSION = "0.2.1"

    const val VERSION_JAVA = 21
    const val VERSION_MINECRAFT = "1.21.1"
}

plugins {
    id("fabric-loom") version "1.7.4"
}

base {
    group = "io.github.startsmercury.luminous_no_shading"
    archivesName = "luminous-no-shading"
    version = createVersionString()
}

java {
    withSourcesJar()

    toolchain {
        languageVersion = JavaLanguageVersion.of(Constants.VERSION_JAVA)
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
    maven {
        name = "Modrinth Maven"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${Constants.VERSION_MINECRAFT}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.16.5")

    modCompileOnly("maven.modrinth:sodium:mc1.21-0.6.0-beta.2-fabric")
    modCompileOnly(fabricApi.module("fabric-rendering-data-attachment-v1", "0.104.0+1.21.1"))
}

tasks.withType<ProcessResources> {
    val data = mapOf(
        "version" to Constants.VERSION,
        "version_java" to Constants.VERSION_JAVA,
        "version_minecraft" to Constants.VERSION_MINECRAFT,
    )

    inputs.properties(data)

    filesMatching("fabric.mod.json") {
        expand(data)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = Constants.VERSION_JAVA
}

fun createVersionString(): String {
    val builder = StringBuilder()

    val isReleaseBuild = project.hasProperty("build.release")
    val buildId = System.getenv("GITHUB_RUN_NUMBER")

    if (isReleaseBuild) {
        builder.append(Constants.VERSION)
    } else {
        builder.append(Constants.VERSION.substringBefore('-'))
        builder.append("-snapshot")
    }

    builder.append("+mc").append(Constants.VERSION_MINECRAFT)

    if (!isReleaseBuild) {
        if (buildId != null) {
            builder.append("-build.${buildId}")
        } else {
            builder.append("-local")
        }
    }

    return builder.toString()
}
