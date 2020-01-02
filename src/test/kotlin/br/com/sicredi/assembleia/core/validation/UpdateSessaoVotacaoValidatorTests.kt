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

class UpdateSessaoVotacaoValidatorTests {

    private val clock: Clock = mock()
    private lateinit var validator: UpdateSessaoVotacaoValidator
    private lateinit var pautaPredicate: PautaPredicate
    private val sessaoVotacaoPredicate = SessaoVotacaoPredicate(clock)
    private val storePautaService: StorePautaService = mock()

    @BeforeEach
    fun beforeEach() {
        pautaPredicate = PautaPredicate(storePautaService = storePautaService)
        validator =
            UpdateSessaoVotacaoValidator(pautaPredicate = pautaPredicate, sessaoVotacaoPredicate = sessaoVotacaoPredicate)
    }

    @Test
    fun `returns true when pauta and sessão votação exists`() {
        val sessaoVotacaoId = 2L
        val pautaId = 1L

        val sessaoVotacao = SessaoVotacao(
            id = sessaoVotacaoId,
            pautaId = pautaId,
            startDateTime = null,
            endDateTime = null
        )

        val pauta = Pauta(
            id = sessaoVotacao.pautaId,
            title = "t1",
            description = "d1"
        )

        whenever(storePautaService.getPauta(pautaId)).thenReturn(pauta)

        validator.validate(pautaId, sessaoVotacaoId, sessaoVotacao) shouldBe true
    }

    @Test
    fun `throws exception when pautaId does not exist`() {
        val sessaoVotacaoId = 2L
        val pautaId = 1L
        val sessaoVotacao = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = null
        )

        whenever(storePautaService.getPauta(sessaoVotacao.pautaId)).thenReturn(null)

        val exception = shouldThrow<AssembeiaValidationException> {
            validator.validate(pautaId, sessaoVotacaoId, sessaoVotacao)
        }
        exception.message shouldBe "Pauta ${sessaoVotacao.pautaId} does not exist"
    }
}
