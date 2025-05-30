plugins {
	alias(local.plugins.lombok)
	alias(local.plugins.springboot)
	alias(local.plugins.spring.dependencies)
}

dependencies {
	// The api subproject needs access to both the model and persistence subproject
	implementation(projects.model)
	implementation(projects.persistence)

	// Spring Boot dependencies
	implementation(local.springboot.starter)
	implementation(local.springboot.starter.validation)
	implementation(local.springboot.starter.web)

	// H2 database dependency for in-memory database
	runtimeOnly(local.h2database)

	// FasterXML Jackson support for Java 8 date/time
	implementation(local.jackson.datatype.jsr310)

	// Liquibase core dependency for database migrations
	runtimeOnly(local.liquibase.core)

	// PostgreSQL database driver
	runtimeOnly(local.postgres)

	// Springdoc OpenAPI for providing Swagger documentation
	implementation(local.springdoc.openapi.starter.webmvc)

	// Spring Boot test dependencies
	testImplementation(local.springboot.starter.test)

	// JUnit platform launcher dependency for running JUnit tests
	testRuntimeOnly(local.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
