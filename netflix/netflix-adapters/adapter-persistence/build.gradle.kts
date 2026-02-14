dependencies {
    implementation(project(":netflix-core:core-port"))
    implementation(project(":netflix-core:core-domain"))
    implementation(project(":netflix-commons"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // jpa
    implementation("org.springframework:spring-tx")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly(project(":netflix-core:core-service"))

    integrationImplementation("org.springframework.boot:spring-boot-starter-test")
}