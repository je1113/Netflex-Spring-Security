dependencies {
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-domain"))
    implementation(project(":netflix-commons"))
    runtimeOnly(project(":netflix-adapters:adapter-http"))
    runtimeOnly(project(":netflix-adapters:adapter-persistence"))
}