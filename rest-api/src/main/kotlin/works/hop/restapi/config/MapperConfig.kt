package works.hop.restapi.config

import com.googlecode.jmapper.JMapper
import com.googlecode.jmapper.api.JMapperAPI
import com.googlecode.jmapper.api.JMapperAPI.global
import com.googlecode.jmapper.api.JMapperAPI.mappedClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import works.hop.restapi.entity.HoursEntity
import works.hop.restapi.entity.UserEntity
import works.hop.restapi.model.Hours
import works.hop.restapi.model.User


@Configuration
class MapperConfig {

    @Bean("UserEntityToUser")
    fun userMapper(): JMapper<User, UserEntity> {
        val toUser = JMapperAPI().add(mappedClass(User::class.java).add(global()))
        return JMapper(User::class.java, UserEntity::class.java, toUser)
    }

    @Bean("HoursEntityToHours")
    fun hoursMapper(): JMapper<Hours, HoursEntity> {
        val toHours = JMapperAPI().add(mappedClass(Hours::class.java).add(global()))
        return JMapper(Hours::class.java, HoursEntity::class.java, toHours)
    }
}