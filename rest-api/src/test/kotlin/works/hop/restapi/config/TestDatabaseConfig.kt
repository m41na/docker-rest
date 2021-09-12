package works.hop.restapi.config

import org.springframework.context.annotation.Configuration

@Configuration
class TestDatabaseConfig {

    val postgresUsername: String = "postgres"
    val postgresPassword: String = "postgres"
    val postgresDatabase: String = "postgres"

//    @Bean
//    @Throws(Exception::class)
//    fun inMemoryDS(): DataSource? {
//        val postgres: EmbeddedPostgres = EmbeddedPostgres.builder()
//            .setCleanDataDirectory(true)
//            .setDataDirectory(Files.temporaryFolderPath())
//            .start()
//
//        val dataSource = HikariDataSource()
//        dataSource.jdbcUrl = postgres.getJdbcUrl(postgresUsername, postgresDatabase)
//        dataSource.username = postgresUsername
//        dataSource.password = postgresPassword
//        dataSource.driverClassName = "org.postgresql.Driver"
//        dataSource.maximumPoolSize = 5
//
//        val flyway: Flyway = Flyway.configure()
//            .dataSource(dataSource)
//            .locations("classpath:sql")
//            .load()
//        flyway.migrate()
//
//        return dataSource
//    }
}