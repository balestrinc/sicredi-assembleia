package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import org.springframework.stereotype.Component

@Component
class SessaoVotacaoPredicate() {

    val isPeriodValid: (SessaoVotacao) -> Boolean = { sessaoVotacao ->
        val isIntervalSet = sessaoVotacao.startDateTime != null && sessaoVotacao.endDateTime != null
        val isValidPeriod = isIntervalSet && sessaoVotacao.startDateTime?.isBefore(sessaoVotacao.endDateTime) ?: false

        if (!isIntervalSet || isValidPeriod) true else throw AssembeiaValidationException("StartDateTime cannot be bigger than endDateTime")
    }
}
