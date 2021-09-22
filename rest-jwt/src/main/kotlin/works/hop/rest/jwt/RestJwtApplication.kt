package works.hop.rest.jwt

import org.apache.catalina.Context
import org.apache.catalina.connector.Connector
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean

class JwtTomcatServletWebServerFactory: TomcatServletWebServerFactory() {
	override fun postProcessContext(context: Context) {
		val securityConstraint = SecurityConstraint()
		securityConstraint.userConstraint = "CONFIDENTIAL"
		val securityCollection = SecurityCollection()
		securityCollection.addPattern("/*")
		securityConstraint.addCollection(securityCollection)
		context.addConstraint(securityConstraint)
	}
}

@SpringBootApplication
class RestJwtApplication : CommandLineRunner {

	override fun run(vararg args: String?) {
		println("REST JWT Security has started successfully")
	}

	@Bean
	fun servletContainer():  ServletWebServerFactory {
		val tomcat = JwtTomcatServletWebServerFactory()
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector())
		return tomcat;
	}

	private fun httpToHttpsRedirectConnector(): Connector? {
		val connector = Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL)
		connector.scheme = "https"
		connector.port = 3001
		connector.secure = false
		connector.redirectPort = 3443
		return connector
	}
}

fun main(args: Array<String>) {
	runApplication<RestJwtApplication>(*args)
}
