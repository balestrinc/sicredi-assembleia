package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.Clock
import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.domain.store.StorePautaService
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VotoValidatorTests {

    private val clock: Clock = mock()
    private lateinit var validator: VotoValidator
    private lateinit var pautaPredicate: PautaPredicate
    private val associadoPredicate: AssociadoPredicate = mock()
    private val sessaoVotacaoPredicate = SessaoVotacaoPredicate(clock)
    private val storeSessaoVotacaoService: StoreSessaoVotacaoService = mock()
    private val storePautaService: StorePautaService = mock()

    @BeforeEach
    fun beforeEach() {
        pautaPredicate = PautaPredicate(storePautaService = storePautaService)
        validator =
            VotoValidator(pautaPredicate = pautaPredicate,
                sessaoVotacaoPredicate = sessaoVotacaoPredicate,
                storeSessaoVotacaoService = storeSessaoVotacaoService,
                associadoPredicate = associadoPredicate
                )
    }

    @Test
    fun `returns true when voto is valid`() {
        val pautaId = 12L
        val sessaoId = 34L
        val currentDateTime = LocalDateTime.parse("2020-01-05T10:50:41")
        val startDateTime = LocalDateTime.parse("2020-01-02T10:50:41")
        val endDateTime = LocalDateTime.parse("2020-01-10T10:50:41")

        val sessaoVotacao = SessaoVotacao(
            id = sessaoId,
            pautaId = pautaId,
            startDateTime = startDateTime,
            endDateTime = endDateTime
        )

        val pauta = Pauta(
            id = pautaId,
            title = "t1",
            description = "d1"
        )

        val voto = Voto(
            pautaId = pautaId,
            sessaoId = sessaoId,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        whenever(associadoPredicate.canVote(voto.associadoCPF)).thenReturn(true)
        whenever(clock.now()).thenReturn(currentDateTime)
        whenever(storePautaService.getPauta(voto.pautaId)).thenReturn(pauta)
        whenever(storeSessaoVotacaoService.getSessaoVotacao(voto.sessaoId)).thenReturn(sessaoVotacao)

        validator.validate(voto) shouldBe true
    }

    @Test
    fun `throws exception when sessão does not exist`() {
        val pauta = Pauta(
            id = 1L,
            title = "t1",
            description = "d1"
        )

        val voto = Voto(
            pautaId = 1L,
            sessaoId = 2L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        whenever(storePautaService.getPauta(voto.pautaId)).thenReturn(pauta)
        whenever(storeSessaoVotacaoService.getSessaoVotacao(voto.sessaoId)).thenReturn(null)

        val exception = shouldThrow<AssembeiaValidationException> {
            validator.validate(voto)
        }
        exception.message shouldBe "Sessão ${voto.sessaoId} does not exist"
    }
}
