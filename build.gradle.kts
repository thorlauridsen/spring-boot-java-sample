plugins {
	java
	alias(local.plugins.springboot)
	alias(local.plugins.spring.dependencies)
}

group = "com.github.thorlauridsen"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot dependencies
	implementation(local.springboot.starter)
	implementation(local.springboot.starter.jpa)
	implementation(local.springboot.starter.web)

	// H2 database dependency for in-memory database
	implementation(local.h2database)

	// Liquibase core dependency for database migrations
	implementation(local.liquibase.core)

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
