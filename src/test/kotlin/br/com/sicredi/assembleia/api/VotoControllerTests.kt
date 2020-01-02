package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.support.AssembleiaApiTestSetup
import br.com.sicredi.assembleia.ws.model.VotoRequest
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class VotoControllerTests : AssembleiaApiTestSetup() {

    private val voto = VotoRequest(
        associadoCPF = "62289608068",
        votoOpcao = "Sim"
    )

    @BeforeEach
    fun before() {
        val createdPauta = createPauta()
        existentPautaId = createdPauta.id

        val createdSessao = createSessao()
        existentSessaoId = createdSessao.id

        associadoApiServer.`when`(HttpRequest.request("/users/${voto.associadoCPF}"))
            .respond(
                HttpResponse.response("""{"status":"ABLE_TO_VOTE"}""").withHeader(
                    "Content-Type",
                    "application/json"
                )
            )
    }

    @Test
    fun `vote for a pauta`() {
        val responseBody = webClient
            .post()
            .uri("/api/pauta/$existentPautaId/sessao/$existentSessaoId/voto")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(voto))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        mapper.readValue<Voto>(responseBody!!) should {
            it.id shouldNotBe null
            it.pautaId shouldBe existentPautaId
            it.sessaoId shouldBe existentSessaoId
            it.associadoCPF shouldBe voto.associadoCPF
            it.votoOpcao shouldBe VotoOpcao.SIM
        }
    }

    @Test
    fun `fails when associado already voted`() {
        val responseFirstVote = webClient
            .post()
            .uri("/api/pauta/$existentPautaId/sessao/$existentSessaoId/voto")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(voto))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        val responseSecondVote = webClient
            .post()
            .uri("/api/pauta/$existentPautaId/sessao/$existentSessaoId/voto")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(voto))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .returnResult().responseBody

        mapper.readValue<Voto>(responseFirstVote!!) should {
            it.id shouldNotBe null
            it.pautaId shouldBe existentPautaId
            it.sessaoId shouldBe existentSessaoId
            it.associadoCPF shouldBe voto.associadoCPF
            it.votoOpcao shouldBe VotoOpcao.SIM
        }

        mapper.readValue<ErrorResponse>(responseSecondVote!!) should {
            it.errorMessage shouldBe "Associado já votou nesta sessão de votação"
        }
    }
}
