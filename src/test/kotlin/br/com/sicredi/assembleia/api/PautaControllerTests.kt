package br.com.sicredi.assembleia.api

import io.kotlintest.shouldBe
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
internal class PautaControllerTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `creates pauta`() {
        val requestBody = """{"title":"title p1","description":"description p1"}"""
        val expectedResponse = """{"id":1,"title":"title p1","description":"description p1"}"""

        val result = webClient
            .post()
            .uri("/api/pauta")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult().responseBody

        result shouldBe expectedResponse
    }

    @Test
    fun `returns error when request body is missing description attribute`() {
        val requestBody = """{"title":"title p1"}"""

        webClient
            .post()
            .uri("/api/pauta")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().is4xxClientError
    }
}
