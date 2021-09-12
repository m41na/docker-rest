package works.hop.restapi.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import works.hop.restapi.controller.ApiController
import works.hop.restapi.service.ApiService

@Configuration
class TestControllerConfig {

    @Bean
    fun apiController(@Autowired testApiService: ApiService): ApiController {
        return ApiController(testApiService)
    }
}