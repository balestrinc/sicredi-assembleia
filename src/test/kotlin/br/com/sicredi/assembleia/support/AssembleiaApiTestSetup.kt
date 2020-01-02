package br.com.sicredi.assembleia.support

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest
import br.com.sicredi.assembleia.ws.model.OpenSessaoVotacaoRequest
import br.com.sicredi.assembleia.ws.model.VotoRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.LocalDateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfiguration::class)
class AssembleiaApiTestSetup {

    @Autowired
    lateinit var webClient: WebTestClient

    val mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()

    var existentPautaId: Long = 0
    var existentSessaoId: Long = 0

    lateinit var associadoApiServer: MockServerClient

    @BeforeAll
    fun beforeAll() {
        associadoApiServer = createAndStartMockServer("localhost", 9898)
    }

    @AfterAll
    fun afterAll() {
        associadoApiServer.close()
    }

    fun createPauta(): Pauta {
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

        return mapper.readValue(responseBody!!)
    }

    fun createSessao(): SessaoVotacao {
        val startDateTime = LocalDateTime.parse(TestConstants.CURRENT_TIME)
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

        return mapper.readValue(responseBody!!)
    }

    fun addVoto(voto: VotoRequest): Voto {
        associadoApiServer.`when`(HttpRequest.request("/users/${voto.associadoCPF}"))
            .respond(
                HttpResponse.response("""{"status":"ABLE_TO_VOTE"}""")
                    .withHeader("Content-Type", "application/json")
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

        return mapper.readValue(responseBody!!)
    }
}
