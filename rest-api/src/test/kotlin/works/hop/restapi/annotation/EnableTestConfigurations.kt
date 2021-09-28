package works.hop.restapi.annotation

import org.springframework.context.annotation.Import
import works.hop.restapi.config.MapperConfig
import works.hop.restapi.config.TestControllerConfig
import works.hop.restapi.config.TestRepositoryConfig
import works.hop.restapi.config.TestServiceConfig
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Import(TestRepositoryConfig::class, TestServiceConfig::class, TestControllerConfig::class, MapperConfig::class)
annotation class EnableTestConfigurations
