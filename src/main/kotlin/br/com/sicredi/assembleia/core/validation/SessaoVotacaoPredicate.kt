package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import org.springframework.stereotype.Component

@Component
class SessaoVotacaoPredicate(private val clock: Clock) {

    val isPeriodValid: (SessaoVotacao) -> Boolean = { sessaoVotacao ->
        val isIntervalSet = sessaoVotacao.startDateTime != null && sessaoVotacao.endDateTime != null
        val isValidPeriod = isIntervalSet && sessaoVotacao.startDateTime?.isBefore(sessaoVotacao.endDateTime) ?: false

        if (!isIntervalSet || isValidPeriod) true else throw AssembeiaValidationException("StartDateTime cannot be bigger than endDateTime")
    }

    val isSessaoOpen: (SessaoVotacao) -> Boolean = { sessaoVotacao ->
        val currentTime = clock.now()
        val isBiggerOrEqualStart =
            currentTime.isAfter(sessaoVotacao.startDateTime) || currentTime.isEqual(sessaoVotacao.startDateTime)
        val isSmallerOrEqualEnd =
            currentTime.isBefore(sessaoVotacao.endDateTime) || currentTime.isEqual(sessaoVotacao.endDateTime)
        val isBetween = isBiggerOrEqualStart && isSmallerOrEqualEnd
        if (isBetween) true else throw AssembeiaValidationException("Sessão Votação is not open")
    }

    val sessaoExists: (SessaoVotacao?, sessaoId: Long) -> Boolean = { sessaoVotacao, sessaoId ->
        sessaoVotacao?.let { true } ?: throw AssembeiaValidationException("Sessão $sessaoId does not exist")
    }

    val votacaoNotResulted: (SessaoVotacao) -> Boolean = { sessaoVotacao ->
        if (!sessaoVotacao.votacaoEncerrada) true else throw AssembeiaValidationException("Sessão Votação encerrada")
    }
}
