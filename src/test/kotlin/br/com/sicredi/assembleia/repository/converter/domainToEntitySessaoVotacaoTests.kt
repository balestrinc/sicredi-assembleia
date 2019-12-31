package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.repository.entity.SessaoVotacaoEntity
import io.kotlintest.shouldBe
import java.time.LocalDateTime
import org.junit.jupiter.api.Test

internal class domainToEntitySessaoVotacaoTests {
    @Test
    fun `converts to entity`() {
        val domain = SessaoVotacao(
            pautaId = 1L,
            startDateTime = LocalDateTime.parse("2025-01-05T10:51:40"),
            endDateTime = LocalDateTime.parse("2025-01-10T10:51:40")
        )

        val entity = SessaoVotacaoEntity(
            id = 0,
            pautaId = 1L,
            startDateTime = LocalDateTime.parse("2025-01-05T10:51:40"),
            endDateTime = LocalDateTime.parse("2025-01-10T10:51:40")
        )

        domainToEntitySessaoVotacao(domain) shouldBe entity
    }
}
