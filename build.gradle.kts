import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging
import java.io.FileInputStream
import java.util.Properties

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.6.21"
    application
    id("org.flywaydb.flyway") version "8.5.10"
    id("nu.studer.jooq") version "7.1.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

group = "org.ua.wohnung.bot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val koinVersion = "3.2.2"
val mockkVersion = "1.13.2"
val junitVersion = "5.9.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.insert-koin:koin-core:$koinVersion")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:9.16.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.telegram:telegrambots:6.5.0")

    runtimeOnly("org.postgresql:postgresql:42.5.4")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
    jooqGenerator("org.postgresql:postgresql:42.5.4")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.6")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")

    implementation("com.google.auth:google-auth-library-oauth2-http:1.16.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20230227-2.0.0")

    implementation("com.opencsv:opencsv:5.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.testcontainers:testcontainers:1.17.6")
    testImplementation("org.testcontainers:postgresql:1.17.6")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val secrets = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "src/main/resources/secrets/secrets-dev.properties")))
}

tasks["build"].dependsOn("ktlintFormat")

tasks.withType<Jar> {
    manifest {
        attributes("Main-Class" to "org.ua.wohnung.bot.WohnungsBotApplicationKt")
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.register("dockerBuild") {
    group = "docker"
    dependsOn.add("build")
    doLast {
        exec {
            commandLine("sh", "build-docker.sh")
        }
    }
}

tasks.register("dockerPublish") {
    group = "docker"
    dependsOn.add("dockerBuild")
    doLast {
        exec {
            commandLine("sh", "publish-docker.sh")
        }
    }
}

flyway {
    url = secrets["jdbc_url"] as String
    user = secrets["jdbc_user"] as String
    password = secrets["jdbc_password"] as String
    defaultSchema = "main"
    locations = arrayOf("classpath:db/migration")
    baselineVersion = "-1"
    baselineOnMigrate = true
    table = "flyway_schema_history"
    createSchemas = true
    driver = secrets["driver_class_name"] as String
}

jooq {
    version.set("3.16.4")
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    url = secrets["jdbc_url"] as String
                    driver = secrets["driver_class_name"] as String
                    user = secrets["jdbc_user"] as String
                    password = secrets["jdbc_password"] as String
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "main"
                        excludes = "flyway.*"
                    }
                    generate.apply {
                        isDeprecated = false
                        isPojos = true
                        isImmutablePojos = false
                        isFluentSetters = false
                        withJavaTimeTypes(true)
                    }
                    target.apply {
                        packageName = "org.ua.wohnung.bot.persistence.generated"
                        directory = "src/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}
