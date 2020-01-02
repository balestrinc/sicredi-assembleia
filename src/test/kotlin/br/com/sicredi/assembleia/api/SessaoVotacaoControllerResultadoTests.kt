package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.support.AssembleiaApiTestSetup
import br.com.sicredi.assembleia.ws.model.VotoRequest
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.should
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class SessaoVotacaoControllerResultadoTests : AssembleiaApiTestSetup() {

    @BeforeEach
    fun before() {
        val createdPauta = createPauta()
        existentPautaId = createdPauta.id

        val createdSessao = createSessao()
        existentSessaoId = createdSessao.id

        val votoSim1 = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "Sim"
        )
        val votoSim2 = votoSim1.copy(associadoCPF = "91029574006")

        val votoNao = VotoRequest(
            associadoCPF = "88532257003",
            votoOpcao = "Não"
        )

        addVoto(votoSim1)
        addVoto(votoSim2)
        addVoto(votoNao)
    }

    @Test
    fun `returns results`() {
        val responseBody = webClient
            .put()
            .uri("/api/pauta/$existentPautaId/sessao/$existentSessaoId")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        mapper.readValue<SessaoVotacao>(responseBody!!) should {
            it.totalVotosFavoravel shouldBe 2
            it.totalVotosContrario shouldBe 1
            it.totalVotos shouldBe 3
            it.pautaAprovada shouldBe false
            it.votacaoEncerrada shouldBe false
        }
    }
}
