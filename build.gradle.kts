plugins {
    scala
    java
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.scala-lang:scala3-library_3:3.4.2")
    implementation("org.antlr:antlr4-runtime:4.13.1")

    implementation("org.l33tlabs.twl:pngdecoder:1.0")

    implementation("org.lwjgl:lwjgl-glfw:3.3.3")
    implementation("org.lwjgl:lwjgl-vulkan:3.3.3")
    implementation("org.lwjgl:lwjgl-shaderc:3.3.3")

    runtimeOnly("org.lwjgl:lwjgl:3.3.3:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.3:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-shaderc:3.3.3:natives-linux")

    runtimeOnly("org.lwjgl:lwjgl:3.3.3:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.3:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-shaderc:3.3.3:natives-windows")
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.nxn.Main"
        attributes["Class-Path"] = "scala-library-2.13.12.jar lwjgl-3.3.3.jar lwjgl-3.3.3-natives-windows.jar lwjgl-shaderc-3.3.3-natives-windows.jar lwjgl-shaderc-3.3.3.jar lwjgl-vulkan-3.3.3.jar antlr4-runtime-4.13.1.jar lwjgl-glfw-3.3.3.jar lwjgl-glfw-3.3.3-natives-windows.jar pngdecoder-1.0.jar scala3-library_3-3.4.2.jar"
    }
}

tasks.register<Copy>("copyDependenciesToLib") {
    into("c:\\Users\\root\\IdeaProjects\\NXN\\build\\libs\\")
    from(project.configurations.runtimeClasspath)
}
