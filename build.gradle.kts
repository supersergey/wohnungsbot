import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("org.flywaydb.flyway") version "8.5.10"
    id("nu.studer.jooq") version "7.1.1"
}

group = "org.ua.wohnung.bot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.telegram:telegrambots:6.0.1")
    implementation("org.telegram:telegrambots-abilities:6.0.1")

    runtimeOnly("org.postgresql:postgresql")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
    jooqGenerator("org.postgresql:postgresql:42.3.5")

    implementation("io.github.microutils:kotlin-logging-jvm:2.1.20")
    implementation("commons-logging:commons-logging:1.2")
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

flyway {
    url = project.property("db.url") as String
    user = project.property("db.user") as String
    password = project.property("db.password") as String
    defaultSchema = project.property("db.schema") as String
    locations = arrayOf("classpath:db/migration")
    baselineVersion = "-1"
    baselineOnMigrate = true
    table = "flyway_schema_history"
    createSchemas = true
    driver = project.property("db.driver") as String
}

jooq {
    version.set("3.16.4")
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    url = project.property("db.url") as String
                    driver = project.property("db.driver") as String
                    user = project.property("db.user") as String
                    password = project.property("db.password") as String
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = "flyway.*"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
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