package br.com.sicredi.assembleia.api.converter

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class wsToDomainPautaTests {

    @Test
    fun `converts web service request to domain`() {
        val request = CreatePautaRequest(title = "t1", description = "d1")
        val expectedResult = Pauta(id = 0, title = "t1", description = "d1")

        val result = wsToDomainPauta(request)

        result shouldBe expectedResult
    }
}
