package works.hop.restapi.config

import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Import(TestRepositoryConfig::class, TestServiceConfig::class, TestControllerConfig::class, MapperConfig::class)
annotation class EnableTestConfigurations
