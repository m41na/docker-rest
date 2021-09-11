package works.hop.restapi.config

import com.googlecode.jmapper.JMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.TestPropertySource
import works.hop.restapi.entity.HoursEntity
import works.hop.restapi.entity.UserEntity
import works.hop.restapi.model.Hours
import works.hop.restapi.model.User
import works.hop.restapi.repository.HoursRepository
import works.hop.restapi.repository.UsersRepository
import works.hop.restapi.service.ApiService
import works.hop.restapi.service.IApiService

@Configuration
class TestServiceConfig {

    @Bean
    fun testApiService(@Autowired mockUsersRepository: UsersRepository,
                       @Autowired mockHoursRepository: HoursRepository,
                       @Autowired @Qualifier("UserEntityToUser") userMapper: JMapper<User, UserEntity>,
                       @Autowired @Qualifier("HoursEntityToHours") hoursMapper: JMapper<Hours, HoursEntity>
    ) : IApiService{
        return ApiService(mockUsersRepository, mockHoursRepository, userMapper, hoursMapper)
    }
}