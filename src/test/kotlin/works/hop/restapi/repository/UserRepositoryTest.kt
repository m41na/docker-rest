package works.hop.restapi.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import works.hop.restapi.config.TestDatabaseConfig
import works.hop.restapi.entity.UserEntity

@DataJdbcTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TestDatabaseConfig::class])
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test", "stage")
internal class UserRepositoryTest(@Autowired val usersRepository: UsersRepository) {

    @Test
    fun hoursWorkedByUser() {
        val users: List<UserEntity> = usersRepository.allActiveUsers()
        assertEquals(users.size, 10)
    }
}