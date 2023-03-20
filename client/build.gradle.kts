group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common:http"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}
