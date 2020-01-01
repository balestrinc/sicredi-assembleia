package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import com.nhaarman.mockitokotlin2.mock
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import java.time.LocalDateTime
import org.junit.jupiter.api.Test

internal class SesssaoVotacaoPredicateSessaoExistsTests {

    private val clock: Clock = mock()
    private val sessaoVotacaoPredicate = SessaoVotacaoPredicate(clock)

    @Test
    fun `returns true when sessão votação is defined`() {

        val currentDateTime = LocalDateTime.now()
        val sessaoId = 6L

        val sessaoVotacao = SessaoVotacao(
            id = sessaoId,
            pautaId = 1L,
            startDateTime = currentDateTime.minusDays(1),
            endDateTime = currentDateTime.plusDays(1)
        )
        sessaoVotacaoPredicate.sessaoExists(sessaoVotacao, sessaoId) shouldBe true
    }

    @Test
    fun `throws when sessão votação is null`() {
        val sessaoVotacao: SessaoVotacao? = null
        val sessaoId = 6L

        val exception = shouldThrow<AssembeiaValidationException> {
            sessaoVotacaoPredicate.sessaoExists(sessaoVotacao, sessaoId)
        }
        exception.message shouldBe "Sessão $sessaoId does not exist"
    }
}
