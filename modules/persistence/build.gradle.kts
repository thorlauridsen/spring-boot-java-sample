plugins {
    alias(local.plugins.springboot)
    alias(local.plugins.spring.dependencies)
}

dependencies {
    // The persistence subproject needs access to the model subproject
    implementation(projects.model)

    // Spring Boot dependencies
    implementation(local.springboot.starter)
    implementation(local.springboot.starter.jpa)
}
