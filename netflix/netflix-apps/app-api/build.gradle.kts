dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.data:spring-data-commons")

    implementation("org.flywaydb:flyway-core")

    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-commons"))

    runtimeOnly(project(":netflix-adapters:adapter-http"))
    runtimeOnly(project(":netflix-adapters:adapter-persistence"))
    runtimeOnly(project(":netflix-adapters:adapter-redis"))

    integrationImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    integrationImplementation("io.rest-assured:spring-mock-mvc")

    integrationImplementation("com.epages:restdocs-api-spec-mockmvc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val appMainClassName = "fast.campus.netplix.NetflixApiApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}