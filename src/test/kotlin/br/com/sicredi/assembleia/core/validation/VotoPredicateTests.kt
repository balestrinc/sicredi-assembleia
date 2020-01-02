package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test

internal class VotoPredicateTests {

    private val storeVotoService: StoreVotoService = mock()
    private val votoPredicate: VotoPredicate = VotoPredicate(storeVotoService = storeVotoService)

    @Test
    fun `returns true when associado did not vote yet`() {

        val voto = Voto(
            pautaId = 1L,
            sessaoId = 2L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        whenever(storeVotoService.getVotoAssocado(voto.sessaoId, voto.associadoCPF)).thenReturn(null)

        votoPredicate.associadoDidNotVote(voto) shouldBe true
    }

    @Test
    fun `Throws when associado already voted`() {
        val voto = Voto(
            pautaId = 1L,
            sessaoId = 2L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        whenever(storeVotoService.getVotoAssocado(voto.sessaoId, voto.associadoCPF)).thenReturn(voto)

        val exception = shouldThrow<AssembeiaValidationException> {
            votoPredicate.associadoDidNotVote(voto)
        }
        exception.message shouldBe "Associado já votou nesta sessão de votação"
    }
}
