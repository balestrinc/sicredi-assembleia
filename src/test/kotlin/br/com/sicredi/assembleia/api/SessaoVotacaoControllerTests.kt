package br.com.sicredi.assembleia.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class SessaoVotacaoControllerTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `opens sessão votação with defined time`() {
        val requestBody = """{"startDateTime":"2020-01-01T10:30:00","endDateTime":"2020-01-02T10:30:00"}"""
        val pautaId = 1

        webClient
            .post()
            .uri("/api/pauta/$pautaId/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().is2xxSuccessful
    }

    @Test
    fun `opens sessão votação without defining time`() {
        val requestBody = """{}"""
        val pautaId = 1

        webClient
            .post()
            .uri("/api/pauta/$pautaId/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().is2xxSuccessful
    }
}
