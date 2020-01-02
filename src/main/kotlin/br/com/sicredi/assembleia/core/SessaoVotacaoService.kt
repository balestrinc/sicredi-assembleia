package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.core.validation.OpenSessaoVotacaoValidator
import br.com.sicredi.assembleia.core.validation.UpdateSessaoVotacaoValidator
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
import java.time.Duration
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class ClockImpl : Clock {
    override fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}

interface Clock {
    fun now(): LocalDateTime
}

@Service
class SessaoVotacaoService(
    @Value("\${system.sessaoVotacao.defaultDuration}") private val defaultSessaoVotacaoDuration: Duration,
    private val storeSessaoVotacaoService: StoreSessaoVotacaoService,
    private val votacaoFactory: VotacaoFactory,
    private val openSessaoVotacaoValidator: OpenSessaoVotacaoValidator,
    private val updateSessaoVotacaoValidator: UpdateSessaoVotacaoValidator,
    private val clock: Clock
) {
    fun open(sessaoVotacao: SessaoVotacao): SessaoVotacao {
        openSessaoVotacaoValidator.validate(sessaoVotacao)
        return storeSessaoVotacaoService.open(setDuration(sessaoVotacao))
    }

    fun updateVotacaoResultado(pautaId: Long, sessaoId: Long): SessaoVotacao {
        val sessaoVotacao = storeSessaoVotacaoService.getSessaoVotacao(sessaoId)
        updateSessaoVotacaoValidator.validate(pautaId, sessaoId, sessaoVotacao)
        val votacao = votacaoFactory.buildVotacao(sessaoVotacao!!)
        return storeSessaoVotacaoService.update(votacao)
    }

    private fun setDuration(sessaoVotacao: SessaoVotacao): SessaoVotacao {
        if ((sessaoVotacao.startDateTime == null) or (sessaoVotacao.endDateTime == null)) {
            val currentTime: LocalDateTime = clock.now()
            val endTime = currentTime.plusSeconds(defaultSessaoVotacaoDuration.toSeconds())
            return sessaoVotacao.copy(
                startDateTime = currentTime,
                endDateTime = endTime
            )
        }
        return sessaoVotacao
    }
}
