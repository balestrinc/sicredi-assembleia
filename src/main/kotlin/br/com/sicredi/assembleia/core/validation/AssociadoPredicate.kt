package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.client.AssociadoApiClient
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import org.springframework.stereotype.Component

@Component
class AssociadoPredicate(private val associadoApiClient: AssociadoApiClient) {
    fun cpfEnabledToVote(cpf: String): Boolean {
        val isAbleToVote = associadoApiClient
            .checkAssociadoCPF(cpf)
            .map { associadoCPFValidationResponse ->
                associadoCPFValidationResponse.status == "ABLE_TO_VOTE"
            }
            .onErrorMap { AssembeiaValidationException("CPF Inválido") }
            .block() // todo make all reactive
        return if (isAbleToVote == true) true else throw AssembeiaValidationException("CPF não autorizado para votar")
    }
}
