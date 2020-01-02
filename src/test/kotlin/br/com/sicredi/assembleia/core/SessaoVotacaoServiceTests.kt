package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.core.validation.OpenSessaoVotacaoValidator
import br.com.sicredi.assembleia.core.validation.UpdateSessaoVotacaoValidator
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
import br.com.sicredi.assembleia.messaging.AssembleiaTopics
import br.com.sicredi.assembleia.messaging.MessagePublisher
import br.com.sicredi.assembleia.support.FakeClock
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import java.time.Duration
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SessaoVotacaoServiceTests {
    private val defaultSessaoVotacaoDuration = Duration.parse("PT60S")
    private lateinit var sessaoVotacaoService: SessaoVotacaoService
    private val storeSessaoVotacaoService: StoreSessaoVotacaoService = mock()
    private val votacaoFactory: VotacaoFactory = mock()
    private val openSessaoVotacaoValidator: OpenSessaoVotacaoValidator = mock()
    private val updateSessaoVotacaoValidator: UpdateSessaoVotacaoValidator = mock()
    private val messagePublisher: MessagePublisher = mock()

    private val currentTime = LocalDateTime.parse("2020-01-01T10:50:40")
    private val fakeClock: Clock = FakeClock(currentTime) // todo use mock() instead

    @BeforeEach
    fun beforeEach() {
        sessaoVotacaoService = SessaoVotacaoService(
            defaultSessaoVotacaoDuration = defaultSessaoVotacaoDuration,
            storeSessaoVotacaoService = storeSessaoVotacaoService,
            votacaoFactory = votacaoFactory,
            openSessaoVotacaoValidator = openSessaoVotacaoValidator,
            updateSessaoVotacaoValidator = updateSessaoVotacaoValidator,
            messagePublisher = messagePublisher,
            clock = fakeClock
        )
    }

    @Test
    fun `opens a sessão votação informing time`() {
        val now = LocalDateTime.now()
        val tomorrow = now.plusDays(1)

        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = now,
            endDateTime = tomorrow
        )

        val savedSessao = openRequest.copy(id = 5L)

        whenever(openSessaoVotacaoValidator.validate(any())).thenReturn(true)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(savedSessao)

        val result = sessaoVotacaoService.open(openRequest)
        result shouldBe savedSessao
    }

    @Test
    fun `opens a sessão votação with default time`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = null
        )

        val endDateTime = LocalDateTime.parse("2020-01-01T10:51:40")

        val expectedDefault = openRequest.copy(
            startDateTime = currentTime,
            endDateTime = endDateTime
        )
        val savedSessao = expectedDefault.copy(id = 5L)

        whenever(openSessaoVotacaoValidator.validate(any())).thenReturn(true)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(savedSessao)

        val result = sessaoVotacaoService.open(openRequest)

        verify(storeSessaoVotacaoService).open(expectedDefault)
        result shouldBe savedSessao
    }

    @Test
    fun `opens a sessão votação with default time when endDateTime is null`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = LocalDateTime.parse("2025-01-05T10:51:40"),
            endDateTime = null
        )

        val expectedDefault = openRequest.copy(
            startDateTime = currentTime,
            endDateTime = currentTime.plusSeconds(defaultSessaoVotacaoDuration.toSeconds())
        )
        val savedSessao = expectedDefault.copy(id = 5L)

        whenever(openSessaoVotacaoValidator.validate(any())).thenReturn(true)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(savedSessao)

        val result = sessaoVotacaoService.open(openRequest)

        verify(storeSessaoVotacaoService).open(expectedDefault)
        result shouldBe savedSessao
    }

    @Test
    fun `opens a sessão votação with default time when startDateTime is null`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = LocalDateTime.parse("2025-01-05T10:51:40")
        )

        val expectedDefault = openRequest.copy(
            startDateTime = currentTime,
            endDateTime = currentTime.plusSeconds(defaultSessaoVotacaoDuration.toSeconds())
        )
        val savedSessao = expectedDefault.copy(id = 5L)

        whenever(openSessaoVotacaoValidator.validate(any())).thenReturn(true)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(savedSessao)

        val result = sessaoVotacaoService.open(openRequest)

        verify(storeSessaoVotacaoService).open(expectedDefault)
        result shouldBe savedSessao
    }

    @Test
    fun `open throws exception when validation fail`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = null
        )

        val expectedException = RuntimeException("Error")

        whenever(openSessaoVotacaoValidator.validate(any())).thenThrow(expectedException)

        val exception = shouldThrow<RuntimeException> {
            sessaoVotacaoService.open(openRequest)
        }
        exception.message shouldBe expectedException.message

        verify(storeSessaoVotacaoService, never()).open(any())
    }

    @Test
    fun `updates a sessão votação`() {
        val now = LocalDateTime.now()
        val tomorrow = now.plusDays(1)

        val sessaoVotacaoId = 2L
        val pautaId = 1L

        val sessaoVotacao = SessaoVotacao(
            id = sessaoVotacaoId,
            pautaId = pautaId,
            startDateTime = now,
            endDateTime = tomorrow
        )

        val builtSessaoVotacao = sessaoVotacao.copy()

        whenever(updateSessaoVotacaoValidator.validate(pautaId, sessaoVotacaoId, sessaoVotacao)).thenReturn(true)
        whenever(storeSessaoVotacaoService.getSessaoVotacao(sessaoVotacaoId)).thenReturn(sessaoVotacao)
        whenever(votacaoFactory.buildVotacao(sessaoVotacao)).thenReturn(builtSessaoVotacao)
        whenever(storeSessaoVotacaoService.update(builtSessaoVotacao)).thenReturn(builtSessaoVotacao)

        val result = sessaoVotacaoService.updateVotacaoResultado(pautaId, sessaoVotacaoId)

        result shouldBe builtSessaoVotacao

        verify(messagePublisher, never()).publish(any(), any())
    }

    @Test
    fun `publishes message when votacaoEncerrada equals true`() {
        val startDateTime = LocalDateTime.now().minusDays(3)
        val endDateTime = startDateTime.plusDays(1)

        val sessaoVotacaoId = 2L
        val pautaId = 1L

        val sessaoVotacao = SessaoVotacao(
            id = sessaoVotacaoId,
            pautaId = pautaId,
            startDateTime = startDateTime,
            endDateTime = endDateTime
        )

        val builtSessaoVotacao = sessaoVotacao.copy(
            votacaoEncerrada = true
        )

        whenever(updateSessaoVotacaoValidator.validate(pautaId, sessaoVotacaoId, sessaoVotacao)).thenReturn(true)
        whenever(storeSessaoVotacaoService.getSessaoVotacao(sessaoVotacaoId)).thenReturn(sessaoVotacao)
        whenever(votacaoFactory.buildVotacao(sessaoVotacao)).thenReturn(builtSessaoVotacao)
        whenever(storeSessaoVotacaoService.update(builtSessaoVotacao)).thenReturn(builtSessaoVotacao)

        val result = sessaoVotacaoService.updateVotacaoResultado(pautaId, sessaoVotacaoId)

        result shouldBe builtSessaoVotacao

        verify(messagePublisher).publish(AssembleiaTopics.VOTACAO_RESULTED, builtSessaoVotacao)
    }
}
