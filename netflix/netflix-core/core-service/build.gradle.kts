dependencies {
    implementation("org.springframework:spring-context")
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-port"))
    implementation(project(":netflix-core:core-domain"))
    implementation(project(":netflix-commons"))
}