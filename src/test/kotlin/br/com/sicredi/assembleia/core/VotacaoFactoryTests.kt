package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VotacaoFactoryTests {
    private lateinit var votacaoFactory: VotacaoFactory
    private val storeVotoService: StoreVotoService = mock()

    private val fakeClock: Clock = mock()

    private val sessaoVotacaoId = 1L

    private val currentTime = LocalDateTime.now()

    private val sessaoVotacao = SessaoVotacao(
        id = sessaoVotacaoId,
        pautaId = 1L,
        startDateTime = currentTime.minusDays(1),
        endDateTime = currentTime.plusDays(1)
    )

    @BeforeEach
    fun beforeEach() {
        votacaoFactory = VotacaoFactory(
            storeVotoService = storeVotoService,
            clock = fakeClock
        )
    }

    @Test
    fun `set votes and keep flags false when sessão is open`() {
        val expectedResult = sessaoVotacao.copy(
            totalVotos = 10,
            totalVotosFavoravel = 6,
            totalVotosContrario = 4,
            pautaAprovada = false,
            votacaoEncerrada = false
        )

        whenever(fakeClock.now()).thenReturn(currentTime)
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId)).thenReturn(10)
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId, VotoOpcao.SIM)).thenReturn(6)

        val result = votacaoFactory.buildVotacao(sessaoVotacao)
        result shouldBe expectedResult
    }

    @Test
    fun `set pautaAprovada = true when current time is bigger than endDateTime and votos SIM are bigger`() {
        val expectedResult = sessaoVotacao.copy(
            totalVotos = 10,
            totalVotosFavoravel = 6,
            totalVotosContrario = 4,
            pautaAprovada = true,
            votacaoEncerrada = true
        )

        whenever(fakeClock.now()).thenReturn(currentTime.plusDays(4))
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId)).thenReturn(10)
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId, VotoOpcao.SIM)).thenReturn(6)

        val result = votacaoFactory.buildVotacao(sessaoVotacao)
        result shouldBe expectedResult
    }

    @Test
    fun `set pautaAprovada = false when current time is bigger than endDateTime and votos NAO are bigger`() {
        val expectedResult = sessaoVotacao.copy(
            totalVotos = 10,
            totalVotosFavoravel = 3,
            totalVotosContrario = 7,
            pautaAprovada = false,
            votacaoEncerrada = true
        )

        whenever(fakeClock.now()).thenReturn(currentTime.plusDays(4))
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId)).thenReturn(10)
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId, VotoOpcao.SIM)).thenReturn(3)

        val result = votacaoFactory.buildVotacao(sessaoVotacao)
        result shouldBe expectedResult
    }

    @Test
    fun `set pautaAprovada = false when current time is bigger than endDateTime and votos NAO and SIM are equal`() {
        val expectedResult = sessaoVotacao.copy(
            totalVotos = 10,
            totalVotosFavoravel = 5,
            totalVotosContrario = 5,
            pautaAprovada = false,
            votacaoEncerrada = true
        )

        whenever(fakeClock.now()).thenReturn(currentTime.plusDays(4))
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId)).thenReturn(10)
        whenever(storeVotoService.getTotalVotos(sessaoVotacaoId, VotoOpcao.SIM)).thenReturn(5)

        val result = votacaoFactory.buildVotacao(sessaoVotacao)
        result shouldBe expectedResult
    }
}
