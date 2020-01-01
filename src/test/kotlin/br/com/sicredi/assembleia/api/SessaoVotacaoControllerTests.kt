package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.SicrediAssembleiaApplication
import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.support.FakeClock
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.should
import io.kotlintest.shouldBe
import java.time.Duration
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient

@Configuration
@Import(SicrediAssembleiaApplication::class) // the actual configuration
class TestConfiguration {
    @Bean
    fun clock(): Clock {
        val currentTime = LocalDateTime.parse("2020-01-01T10:50:40")
        return FakeClock(currentTime)
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(PER_CLASS)
@Import(TestConfiguration::class)
internal class SessaoVotacaoControllerTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Value("\${system.sessaoVotacao.defaultDuration}")
    private lateinit var defaultSessaoVotacaoDuration: Duration

    private val mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()

    private var existentPautaId: Long = 0

    @BeforeEach
    fun beforeAll() {
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
        existentPautaId = createdPauta.id
    }

    @Test
    fun `opens sessão votação with defined time`() {
        val requestBody = """{"startDateTime":"2020-01-01T10:30:00","endDateTime":"2020-01-02T10:30:00"}"""

        webClient
            .post()
            .uri("/api/pauta/$existentPautaId/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().is2xxSuccessful
    }

    @Test
    fun `opens sessão votação without defining time`() {
        val requestBody = """{}"""

        val responseBody = webClient
            .post()
            .uri("/api/pauta/$existentPautaId/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        val sessao = mapper.readValue<SessaoVotacao>(responseBody!!)

        val startDateTime = LocalDateTime.parse("2020-01-01T10:50:40")
        val endDateTime = startDateTime.plusSeconds(defaultSessaoVotacaoDuration.toSeconds())

        sessao should {
            it.id shouldBeGreaterThan 0
            it.startDateTime shouldBe startDateTime
            it.endDateTime shouldBe endDateTime
        }
    }

    @Test
    fun `fail when pauta does not exist`() {
        val requestBody = """{}"""
        val pautaId = 999
        val expectedErrorResponse = ErrorResponse(
            status = "BAD_REQUEST",
            errorMessage = "Pauta $pautaId does not exist"
        )

        val responseBody = webClient
            .post()
            .uri("/api/pauta/$pautaId/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .returnResult().responseBody

        mapper.readValue<ErrorResponse>(responseBody!!) shouldBe expectedErrorResponse
    }
}
