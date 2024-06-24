
plugins {
    scala
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.scala-lang:scala3-library_3:3.4.2")
    implementation("org.antlr:antlr4-runtime:4.13.1")

    implementation("org.lwjgl:lwjgl-glfw:3.3.3")
    implementation("org.lwjgl:lwjgl-vulkan:3.3.3")
    implementation("org.lwjgl:lwjgl-shaderc:3.3.3")

    runtimeOnly("org.lwjgl:lwjgl:3.3.3:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.3:natives-linux")

    runtimeOnly("org.lwjgl:lwjgl:3.3.3:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.3:natives-windows")
}
