package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.store.StorePautaService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OpenSessaoVotacaoValidatorTests {

    private val clock: Clock = mock()
    private lateinit var validatorOpen: OpenSessaoVotacaoValidator
    private lateinit var pautaPredicate: PautaPredicate
    private val sessaoVotacaoPredicate = SessaoVotacaoPredicate(clock)
    private val storePautaService: StorePautaService = mock()

    @BeforeEach
    fun beforeEach() {
        pautaPredicate = PautaPredicate(storePautaService = storePautaService)
        validatorOpen =
            OpenSessaoVotacaoValidator(pautaPredicate = pautaPredicate, sessaoVotacaoPredicate = sessaoVotacaoPredicate)
    }

    @Test
    fun `returns true when sessão votação is valid`() {
        val sessaoVotacao = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = null
        )

        val pauta = Pauta(
            id = sessaoVotacao.pautaId,
            title = "t1",
            description = "d1"
        )

        whenever(storePautaService.getPauta(sessaoVotacao.pautaId)).thenReturn(pauta)

        validatorOpen.validate(sessaoVotacao) shouldBe true
    }

    @Test
    fun `throws exception when pautaId does not exist`() {
        val sessaoVotacao = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = null
        )

        whenever(storePautaService.getPauta(sessaoVotacao.pautaId)).thenReturn(null)

        val exception = shouldThrow<AssembeiaValidationException> {
            validatorOpen.validate(sessaoVotacao)
        }
        exception.message shouldBe "Pauta ${sessaoVotacao.pautaId} does not exist"
    }
}
