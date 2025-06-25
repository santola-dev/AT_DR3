plugins {
    application
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.1.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.javalin:javalin-testtools:6.1.3")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.example.App")
}

