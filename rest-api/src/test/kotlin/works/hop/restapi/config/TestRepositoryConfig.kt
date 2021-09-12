package works.hop.restapi.config

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import works.hop.restapi.entity.HoursEntity
import works.hop.restapi.entity.UserEntity
import works.hop.restapi.repository.HoursRepository
import works.hop.restapi.repository.UsersRepository

@Configuration
class TestRepositoryConfig {

    @Bean
    fun mockHoursRepository(@Autowired mapper: ObjectMapper): HoursRepository {
        val mockRepo: HoursRepository = mock(HoursRepository::class.java)
        val typeReference: TypeReference<List<HoursEntity>> = object : TypeReference<List<HoursEntity>>() {}
        val jsonFile = this::class.java.classLoader.getResource("response/worked_hours.json")?.readText(Charsets.UTF_8)
        var hoursList: List<HoursEntity> = mapper.readValue(jsonFile, typeReference)
        doReturn(hoursList).`when`(mockRepo).hoursWorkedByUser(anyLong())
//        doReturn(1).`when`(mockRepo).updateHours(anyFloat(), notNull(), anyLong())
        return mockRepo
    }

    @Bean
    fun mockUsersRepository(@Autowired mapper: ObjectMapper): UsersRepository {
        val mockRepo: UsersRepository = mock(UsersRepository::class.java)
        val typeReference: TypeReference<List<UserEntity>> = object : TypeReference<List<UserEntity>>() {}
        val jsonFile = this::class.java.classLoader.getResource("response/all_users.json")?.readText(Charsets.UTF_8)
        var usersList: List<UserEntity> = mapper.readValue(jsonFile, typeReference)
        `when`(mockRepo.allActiveUsers()).thenReturn(usersList)
        return mockRepo
    }
}