package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import com.nhaarman.mockitokotlin2.mock
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import java.time.LocalDateTime
import org.junit.jupiter.api.Test

internal class SesssaoVotacaoPredicateIsPeriodValidTests {

    private val clock: Clock = mock()
    private val sessaoVotacaoPredicate = SessaoVotacaoPredicate(clock)

    @Test
    fun `returns true when startDatetime and endDateTime are defined and valid`() {

        val now = LocalDateTime.now()
        val tomorrow = now.plusDays(1)

        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = now,
            endDateTime = tomorrow
        )

        sessaoVotacaoPredicate.isPeriodValid(openRequest) shouldBe true
    }

    @Test
    fun `returns true when startDatetime is defined but endDateTime is null`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = LocalDateTime.now(),
            endDateTime = null
        )

        sessaoVotacaoPredicate.isPeriodValid(openRequest) shouldBe true
    }

    @Test
    fun `returns true when startDatetime is null and endDateTime is defined`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = LocalDateTime.now()
        )

        sessaoVotacaoPredicate.isPeriodValid(openRequest) shouldBe true
    }

    @Test
    fun `throws exception when startDatetime is after endDateTime`() {
        val now = LocalDateTime.now()
        val tomorrow = now.plusDays(1)

        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = tomorrow,
            endDateTime = now
        )

        val exception = shouldThrow<AssembeiaValidationException> {
            sessaoVotacaoPredicate.isPeriodValid(openRequest)
        }
        exception.message shouldBe "StartDateTime cannot be bigger than endDateTime"
    }
}
