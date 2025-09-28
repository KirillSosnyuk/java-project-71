plugins {
    id("java")
    id("application")
    id("com.github.ben-manes.versions") version "0.47.0"
    id("org.sonarqube") version "6.3.1.5724"
    checkstyle
    jacoco
}

sonar {
    properties {
        property("sonar.projectKey", "KirillSosnyuk_java-project-71")
        property("sonar.organization", "kirillsosnyuk")
    }
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("org.yaml:snakeyaml:2.5")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

application {
    mainClass.set("hexlet.code.App")
}
