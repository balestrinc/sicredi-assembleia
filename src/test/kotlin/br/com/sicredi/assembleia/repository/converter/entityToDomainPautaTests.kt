package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.repository.entity.PautaEntity
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class entityToDomainPautaTests {

    @Test
    fun `converts pauta from entity to domain`() {
        val entity = PautaEntity(id = 1L, title = "t1", description = "d1")
        val expectedResult = Pauta(id = 1L, title = "t1", description = "d1")

        val result = entityToDomainPauta(entity)

        result shouldBe expectedResult
    }
}
