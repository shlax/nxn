plugins {
    scala
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.scala-lang:scala3-library_3:3.5.0")
    implementation("org.antlr:antlr4-runtime:4.13.2")

    implementation("org.l33tlabs.twl:pngdecoder:1.0")

    implementation("org.lwjgl:lwjgl-glfw:3.3.4")
    implementation("org.lwjgl:lwjgl-vulkan:3.3.4")
    implementation("org.lwjgl:lwjgl-shaderc:3.3.4")

    runtimeOnly("org.lwjgl:lwjgl:3.3.4:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.4:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-shaderc:3.3.4:natives-linux")

    runtimeOnly("org.lwjgl:lwjgl:3.3.4:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.4:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-shaderc:3.3.4:natives-windows")
}

tasks.register<Copy>("copyDependenciesToLibs") {
    into("${layout.buildDirectory.get()}/libs")
    from(project.configurations.runtimeClasspath)
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.nxn.Main"
        attributes["Class-Path"] = project.configurations.runtimeClasspath.get().files.joinToString(" ") { it.name }
    }
}
