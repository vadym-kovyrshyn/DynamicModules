plugins {
    id("java")
    id("java-library-distribution")
}

group = "org.enterprise"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    archiveFileName.set(rootProject.name + ".jar")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
    manifest {
        attributes["Class-Path"] = "libs/tags/"
//        attributes["Class-Path"] = "libs/tags/some-module-1.0-SNAPSHOT.jar libs/tags/some-module-2.0-SNAPSHOT.jar"
    }
    configurations["compileClasspath"].forEach { file: File ->
            from(zipTree(file.absoluteFile))
    }
}
