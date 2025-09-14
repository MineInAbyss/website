plugins {
    application
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
}


repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    implementation("me.dvyy:shocky:0.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(21)
}

sourceSets {
    main {
        kotlin.srcDirs("src")
    }
}

application {
    mainClass = "MainKt"
}

tasks {
    register("generate") {
        run.get().args("generate")
        finalizedBy(run)
    }
    register("serve") {
        run.get().args("serve")
        finalizedBy(run)
    }
}
