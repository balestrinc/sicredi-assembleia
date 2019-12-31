package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.store.StorePautaService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PautaPredicateTests {

    private lateinit var pautaPredicate: PautaPredicate
    private val storePautaService: StorePautaService = mock()

    @BeforeEach
    fun beforeEach() {
        pautaPredicate = PautaPredicate(storePautaService = storePautaService)
    }

    @Test
    fun `returns true when pautaId exists`() {
        val pautaId = 1L

        val pauta = Pauta(
            id = pautaId,
            title = "t1",
            description = "d1"
        )

        whenever(storePautaService.getPauta(pautaId)).thenReturn(pauta)

        pautaPredicate.pautaExists(pautaId) shouldBe true
    }

    @Test
    fun `throws exception when pautaId does not exist`() {
        val pautaId = 1L

        whenever(storePautaService.getPauta(pautaId)).thenReturn(null)

        val exception = shouldThrow<AssembeiaValidationException> {
            pautaPredicate.pautaExists(pautaId)
        }
        exception.message shouldBe "Pauta $pautaId does not exist"
    }
}
