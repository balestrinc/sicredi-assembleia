package br.com.sicredi.assembleia.core.client

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

data class AssociadoCPFValidationResponse(
    val status: String
)

@Component
class AssociadoApiClient(
    private val webClient: WebClient
) {

    fun checkAssociadoCPF(cpf: String): Mono<AssociadoCPFValidationResponse> {
        return webClient
            .get()
            .uri("/users/$cpf")
            .retrieve()
            .bodyToMono(AssociadoCPFValidationResponse::class.java)
    }
}
