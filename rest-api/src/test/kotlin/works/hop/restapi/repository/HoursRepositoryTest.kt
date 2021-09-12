package works.hop.restapi.repository

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import works.hop.restapi.entity.HoursEntity
import kotlin.test.assertNotNull

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Disabled("not working as expected - problem with containers. Revisit later one")
internal class HoursRepositoryTest {

    @Autowired
    private lateinit var hoursRepository: HoursRepository

    companion object {
        @Container
        private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("postgres")
            withUsername("postgres")
            withPassword("postgres")
            start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

    @Test
    fun hoursWorkedByUser() {
        val hours: List<HoursEntity> = hoursRepository.hoursWorkedByUser(1L)
        assertNotNull(hours)
    }
}

//
//@ExtendWith(SpringExtension::class)
//@ContextConfiguration(classes = [TestDatabaseConfig::class])
//@TestPropertySource("classpath:application-intg.properties")
//internal class HoursRepositoryTest(@Autowired val hoursRepository: HoursRepository) {
//
//    @Test
//    fun hoursWorkedByUser() {
//        val hours: List<HoursEntity> = hoursRepository.hoursWorkedByUser(10L)
//        assertEquals(hours.size, 4)
//    }
//}