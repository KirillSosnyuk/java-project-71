plugins {
    id("application")
    id("com.github.ben-manes.versions") version "0.47.0"
    id("org.sonarqube") version "6.3.1.5724"
    checkstyle
    jacoco
}

jacoco {
    toolVersion = "0.8.12"
}

sonar {
    properties {
        property("sonar.projectKey", "KirillSosnyuk_java-project-71")
        property("sonar.organization", "kirillsosnyuk")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml")
                .get()
                .asFile
                .absolutePath
        )

        // JUnit XML report
        property(
            "sonar.junit.reportPaths",
            layout.buildDirectory.dir("test-results/test")
                .get()
                .asFile
                .absolutePath
        )
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
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("org.yaml:snakeyaml:2.5")
}

tasks.test {
    useJUnitPlatform()
    reports {
        junitXml.required.set(true)
        junitXml.outputLocation.set(layout.buildDirectory.dir("test-results/test"))
        html.required.set(true)
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("$buildDir/reports/jacoco/test/jacocoTestReport.xml"))
        html.required.set(true)
    }
}

application {
    mainClass.set("hexlet.code.App")
}
