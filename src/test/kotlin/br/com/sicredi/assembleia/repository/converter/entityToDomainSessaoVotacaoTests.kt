package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.repository.entity.SessaoVotacaoEntity
import io.kotlintest.shouldBe
import java.time.LocalDateTime
import org.junit.jupiter.api.Test

internal class entityToDomainSessaoVotacaoTests {

    @Test
    fun `converts entity to domain`() {
        val startDateTime = LocalDateTime.now()
        val endDateTime = startDateTime.plusDays(1)
        val entity = SessaoVotacaoEntity(
            id = 5L,
            pautaId = 1L,
            startDateTime = startDateTime,
            endDateTime = endDateTime
        )

        val domain = SessaoVotacao(
            id = 5L,
            pautaId = 1L,
            startDateTime = startDateTime,
            endDateTime = endDateTime
        )

        entityToDomainSessaoVotacao(entity) shouldBe domain
    }
}
