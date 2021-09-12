package works.hop.restapi

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RestApiApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("REST API application has started successfully")
    }
}

fun main(args: Array<String>) {
    runApplication<RestApiApplication>(*args)
}
