package works.hop.restapi.config

import com.zaxxer.hikari.HikariDataSource
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class TestDatabaseConfig {

    val postgresUsername: String = "postgres"
    val postgresPassword: String = "postgres"
    val postgresDatabase: String = "postgres"

    @Bean
    @Throws(Exception::class)
    fun inMemoryDS(): DataSource? {
        val postgres: EmbeddedPostgres = EmbeddedPostgres.builder()
            .start()

        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = postgres.getJdbcUrl(postgresUsername, postgresDatabase)
        dataSource.username = postgresUsername
        dataSource.password = postgresPassword
        dataSource.driverClassName = "org.postgresql.Driver"
        dataSource.maximumPoolSize = 5

        val flyway: Flyway = Flyway.configure()
            .dataSource(dataSource)
            .load()
        flyway.migrate()

        return dataSource
    }
}