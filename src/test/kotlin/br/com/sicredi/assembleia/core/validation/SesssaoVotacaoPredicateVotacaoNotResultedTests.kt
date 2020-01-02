package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import com.nhaarman.mockitokotlin2.mock
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import java.time.LocalDateTime
import org.junit.jupiter.api.Test

internal class SesssaoVotacaoPredicateVotacaoNotResultedTests {

    private val clock: Clock = mock()
    private val sessaoVotacaoPredicate = SessaoVotacaoPredicate(clock)

    @Test
    fun `returns true when votacaoEncerrada is false`() {
        val sessaoVotacao = SessaoVotacao(
            id = 6L,
            pautaId = 1L,
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now(),
            votacaoEncerrada = false
        )
        sessaoVotacaoPredicate.votacaoNotResulted(sessaoVotacao) shouldBe true
    }

    @Test
    fun `throws when votacaoEncerrada is true`() {
        val sessaoVotacao = SessaoVotacao(
            id = 3L,
            pautaId = 1L,
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now(),
            votacaoEncerrada = true
        )
        val exception = shouldThrow<AssembeiaValidationException> {
            sessaoVotacaoPredicate.votacaoNotResulted(sessaoVotacao)
        }
        exception.message shouldBe "Sessão Votação encerrada"
    }
}
