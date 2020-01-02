package br.com.sicredi.assembleia

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.StringVendorExtension
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Configuration {

    @Bean
    fun webClient(builder: WebClient.Builder, @Value("\${associado.api.address}") baseurl: String): WebClient {
        return builder
            .baseUrl(baseurl)
            .build()
    }

    @Bean
    fun api(): Docket {
        val apiDescription = javaClass.getResource("/api-description.md").readText()
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(
                ApiInfo(
                    "Sicredi Assembléia API",
                    apiDescription,
                    "v1",
                    null,
                    "Sicredi",
                    null,
                    null
                )
            )
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.sicredi.assembleia"))
            .paths(PathSelectors.any())
            .build()
    }
}
