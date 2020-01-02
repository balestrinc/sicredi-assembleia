package br.com.sicredi.assembleia

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {

    @Bean
    fun webClient(builder: WebClient.Builder, @Value("\${associado.api.address}") baseurl: String): WebClient {
        return builder
            .baseUrl(baseurl)
            // .exchangeStrategies(
            //     ExchangeStrategies.builder()
            //         .codecs { configurer ->
            //             configurer.customCodecs().decoder(Jackson2XmlDecoder(xmlMapper(), MimeTypeUtils.APPLICATION_XML, MimeTypeUtils.TEXT_XML))
            //         }.build()
            // )
            .build()
    }
}
