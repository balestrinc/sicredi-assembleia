package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.client.AssociadoApiClient
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.support.createAndStartMockServer
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.web.reactive.function.client.WebClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AssociadoPredicateTests {

    private lateinit var associadoPredicate: AssociadoPredicate

    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:9898").build()

    private lateinit var associadoApiServer: MockServerClient

    @BeforeAll
    fun beforeAll() {
        associadoApiServer = createAndStartMockServer("localhost", 9898)
    }

    @AfterAll
    fun afterAll() {
        associadoApiServer.close()
    }

    @Test
    fun `returns true when associado can vote`() {
        val associadoCPF = "80990197093"

        associadoApiServer.`when`(HttpRequest.request("/users/$associadoCPF"))
            .respond(
                HttpResponse.response("""{"status":"ABLE_TO_VOTE"}""")
                    .withHeader("Content-Type", "application/json")
            )

        associadoPredicate = AssociadoPredicate(AssociadoApiClient(webClient))

        associadoPredicate.canVote(associadoCPF) shouldBe true
    }

    @Test
    fun `Throw  when associado cannot vote`() {
        val associadoCPF = "82400072019"

        associadoApiServer.`when`(HttpRequest.request("/users/$associadoCPF"))
            .respond(
                HttpResponse.response("""{"status":"UNABLE_TO_VOTE"}""")
                    .withHeader("Content-Type", "application/json")
            )

        associadoPredicate = AssociadoPredicate(AssociadoApiClient(webClient))

        val exception = shouldThrow<AssembeiaValidationException> {
            associadoPredicate.canVote(associadoCPF)
        }
        exception.message shouldBe "CPF não autorizado para votar"
    }

    @Test
    fun `Throw  when CPF is not valid`() {
        val associadoCPF = "12047546087"

        associadoApiServer.`when`(HttpRequest.request("/users/$associadoCPF"))
            .respond(HttpResponse.response("""""").withStatusCode(404))

        associadoPredicate = AssociadoPredicate(AssociadoApiClient(webClient))

        val exception = shouldThrow<AssembeiaValidationException> {
            associadoPredicate.canVote(associadoCPF)
        }
        exception.message shouldBe "CPF Inválido"
    }
}
