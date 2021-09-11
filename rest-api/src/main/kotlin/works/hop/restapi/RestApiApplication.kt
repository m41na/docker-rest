package works.hop.restapi

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestApiApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Application has started successfully")
    }
}

fun main(args: Array<String>) {
    runApplication<RestApiApplication>(*args)
}
