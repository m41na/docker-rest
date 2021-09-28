package works.hop.restapi.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import works.hop.restapi.controller.RestApiController
import works.hop.restapi.service.UserApiService
import works.hop.restapi.validator.HoursValidator
import works.hop.restapi.validator.RequestValidator
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@Configuration
class TestControllerConfig {

    @Bean
    fun apiController(
        @Autowired testUserApiService: UserApiService,
        @Autowired hoursValidator: RequestValidator
    ): RestApiController {
        return RestApiController(testUserApiService, hoursValidator)
    }

    @Bean
    fun requestValidator(): Validator {
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
        return factory.validator;
    }

    @Bean
    fun hoursValidator(): RequestValidator {
        return HoursValidator()
    }
}