package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.repository.entity.PautaEntity
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class domainToEntityPautaTests {

    @Test
    fun `converts pauta from domain to entity`() {
        val domain = Pauta(id = 1L, title = "t1", description = "d1")
        val expectedResult = PautaEntity(id = 0, title = "t1", description = "d1")

        val result = domainToEntityPauta(domain)

        result shouldBe expectedResult
    }
}
