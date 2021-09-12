package works.hop.rest.jwt

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestJwtApplication : CommandLineRunner {

	override fun run(vararg args: String?) {
		println("REST JWT Security has started successfully")
	}
}

fun main(args: Array<String>) {
	runApplication<RestJwtApplication>(*args)
}
