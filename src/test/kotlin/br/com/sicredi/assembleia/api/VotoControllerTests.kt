package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.ws.model.VotoRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
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
internal class VotoControllerTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    private val mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()

    @Test
    fun `vote for a pauta`() {
        val pautaId = 1
        val sessaoId = 4L

        val voto = VotoRequest(
            associadoCPF = "62289608068",
            votoOpcao = "Sim"
        )

        val responseBody = webClient
            .post()
            .uri("/api/pauta/$pautaId/sessao/$sessaoId/voto")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(voto))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .returnResult().responseBody

        mapper.readValue<Voto>(responseBody!!) should {
            it.id shouldNotBe null
            it.pautaId shouldBe pautaId
            it.sessaoId shouldBe sessaoId
            it.associadoCPF shouldBe voto.associadoCPF
            it.votoOpcao shouldBe VotoOpcao.SIM
        }
    }
}
