package br.com.sicredi.assembleia

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    @Bean
    fun webClient(builder: WebClient.Builder, @Value("\${associado.api.address}") baseurl: String): WebClient {
        return builder
            .baseUrl(baseurl)
            .build()
    }
}
