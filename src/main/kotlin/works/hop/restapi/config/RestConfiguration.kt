package works.hop.restapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor

@Configuration
class RestConfiguration {

    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor? {
        return MethodValidationPostProcessor()
    }
}