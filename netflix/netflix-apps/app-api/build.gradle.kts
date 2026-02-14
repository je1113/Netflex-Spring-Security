dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-commons"))

    runtimeOnly(project(":netflix-adapters:adapter-http"))
    runtimeOnly(project(":netflix-adapters:adapter-persistence"))
}