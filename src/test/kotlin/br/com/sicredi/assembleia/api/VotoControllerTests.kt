package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest
import br.com.sicredi.assembleia.ws.model.OpenSessaoVotacaoRequest
import br.com.sicredi.assembleia.ws.model.VotoRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VotoControllerTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    private val mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()

    private var existentPautaId: Long = 0
    private var existentSessaoId: Long = 0

    @BeforeEach
    fun before() {
        val createdPauta = createPauta()
        existentPautaId = createdPauta.id

        val createdSessao = createSessao()
        existentSessaoId = createdSessao.id
    }

    @Test
    fun `vote for a pauta`() {
        val voto = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "Sim"
        )

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

    private fun createPauta(): Pauta {
        val pauta = CreatePautaRequest(title = "t1", description = "d1")

        val responseBody = webClient
            .post()
            .uri("/api/pauta")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(pauta))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        val createdPauta = mapper.readValue<Pauta>(responseBody!!)
        return createdPauta
    }

    private fun createSessao(): SessaoVotacao {
        val startDateTime = LocalDateTime.now().minusDays(1)
        val endDateTime = startDateTime.plusDays(10)
        val openSessao = OpenSessaoVotacaoRequest(startDateTime = startDateTime, endDateTime = endDateTime)

        val responseBody = webClient
            .post()
            .uri("/api/pauta/$existentPautaId/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(openSessao))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        val createdSessao = mapper.readValue<SessaoVotacao>(responseBody!!)
        return createdSessao
    }
}
