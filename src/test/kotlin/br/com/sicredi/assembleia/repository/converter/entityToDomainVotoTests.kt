package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.repository.entity.VotoEntity
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class entityToDomainVotoTests {

    @Test
    fun `converts to domain`() {
        val domain = Voto(
            pautaId = 1L,
            sessaoId = 3L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        val entity = VotoEntity(
            id = 0,
            pautaId = 1L,
            sessaoId = 3L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        entityToDomainVoto(entity) shouldBe domain
    }
}
