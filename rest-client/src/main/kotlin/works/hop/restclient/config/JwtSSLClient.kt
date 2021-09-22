package works.hop.restclient.config

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.KeyStore

@Configuration
class JwtSSLClient {

    @Bean
    fun restClient(): RestTemplate {

        val keyStore = KeyStore.getInstance("pkcs12")
        val classPathResource = ClassPathResource("/secrets/rest-client-keystore.p12")
        keyStore.load(classPathResource.inputStream, "changeit".toCharArray())

        val socketFactory = SSLConnectionSocketFactory(
            SSLContextBuilder()
                .loadTrustMaterial(keyStore, TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, "changeit".toCharArray()).build(),
            NoopHostnameVerifier.INSTANCE
        )

        val httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
            .setMaxConnTotal(5)
            .setMaxConnPerRoute(5)
            .build()

        val requestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
        requestFactory.setReadTimeout(10000)
        requestFactory.setConnectTimeout(10000)

        val rest = RestTemplate()
        rest.requestFactory = requestFactory

        return rest
    }
}