package br.com.sicredi.assembleia.api.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.ws.model.OpenSessaoVotacaoRequest
import io.kotlintest.shouldBe
import java.time.LocalDateTime
import org.junit.jupiter.api.Test

class wsToDomainSessaoVotacaoTests {

    @Test
    fun `converts web service request to domain`() {
        val start = LocalDateTime.parse("2020-01-01T10:51:40")
        val end = start.plusDays(1)

        val request = OpenSessaoVotacaoRequest(startDateTime = start, endDateTime = end)
        val pautaId = 1L

        val expectedResult = SessaoVotacao(
            pautaId = 1L,
            startDateTime = start,
            endDateTime = end
        )

        val result = wsToDomainSessaoVotacao(pautaId, request)

        result shouldBe expectedResult
    }
}
