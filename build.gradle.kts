object Constants {
    const val VERSION = "0.2.0"

    const val VERSION_JAVA = 21
    const val VERSION_MINECRAFT = "1.20.6"
}

plugins {
    id("fabric-loom") version "1.8.10"
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

}

dependencies {
    minecraft("com.mojang:minecraft:${Constants.VERSION_MINECRAFT}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.16.7")
}

tasks {
    val validateMixinName by registering(net.fabricmc.loom.task.ValidateMixinNameTask::class) {
        source(sourceSets.main.get().output)
        source(sourceSets.named("client").get().output)
    }

    withType<ProcessResources> {
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

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = Constants.VERSION_JAVA
    }
}

/******************************************************************************/
/* COMPATIBILITY TESTS                                                        */
/******************************************************************************/

repositories {
    maven {
        name = "Modrinth Maven"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

run {
    val iris = "maven.modrinth:iris:1.7.2+1.20.6"
    val sodium = "maven.modrinth:sodium:mc1.20.6-0.5.11"

    createCompatTest("iris", iris, sodium)
    createCompatTest("sodium", sodium)
}

dependencies {
    modCompileOnly(fabricApi.module("fabric-rendering-data-attachment-v1", "0.100.8+1.20.6"))
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
