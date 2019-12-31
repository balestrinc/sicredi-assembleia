package br.com.sicredi.assembleia.api.converter

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.ws.model.VotoRequest
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class wsToDomainVotoTests {

    @Test
    fun `converts to Voto when opção is Sim`() {
        val pautaId = 12L
        val sessaoId = 34L
        val votoRequest = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "Sim"
        )

        val expectedResult = Voto(
            pautaId = pautaId,
            sessaoId = sessaoId,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        val result = wsToDomainVoto(pautaId, sessaoId, votoRequest)

        result shouldBe expectedResult
    }

    @Test
    fun `converts to Voto when opção is S`() {
        val pautaId = 12L
        val sessaoId = 34L
        val votoRequest = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "S"
        )

        val expectedResult = Voto(
            pautaId = pautaId,
            sessaoId = sessaoId,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        val result = wsToDomainVoto(pautaId, sessaoId, votoRequest)

        result shouldBe expectedResult
    }

    @Test
    fun `converts to Voto when opção is SIM`() {
        val pautaId = 12L
        val sessaoId = 34L
        val votoRequest = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "SIM"
        )

        val expectedResult = Voto(
            pautaId = pautaId,
            sessaoId = sessaoId,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        val result = wsToDomainVoto(pautaId, sessaoId, votoRequest)

        result shouldBe expectedResult
    }

    @Test
    fun `converts to Voto when opção is yes`() {
        val pautaId = 12L
        val sessaoId = 34L
        val votoRequest = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "yes"
        )

        val expectedResult = Voto(
            pautaId = pautaId,
            sessaoId = sessaoId,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        val result = wsToDomainVoto(pautaId, sessaoId, votoRequest)

        result shouldBe expectedResult
    }

    @Test
    fun `converts to Voto NAO when opção is Não`() {
        val pautaId = 12L
        val sessaoId = 34L
        val votoRequest = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "Não"
        )

        val expectedResult = Voto(
            pautaId = pautaId,
            sessaoId = sessaoId,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.NAO
        )

        val result = wsToDomainVoto(pautaId, sessaoId, votoRequest)

        result shouldBe expectedResult
    }
}
