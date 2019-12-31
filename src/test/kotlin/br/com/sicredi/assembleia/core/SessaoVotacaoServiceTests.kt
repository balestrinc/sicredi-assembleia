package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.core.validation.SessaoVotacaoValidator
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
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
    private val sessaoVotacaoValidator: SessaoVotacaoValidator = mock()

    private val currentTime = LocalDateTime.parse("2020-01-01T10:50:40")
    private val fakeClock: Clock = FakeClock(currentTime)

    @BeforeEach
    fun beforeEach() {
        sessaoVotacaoService = SessaoVotacaoService(
            defaultSessaoVotacaoDuration = defaultSessaoVotacaoDuration,
            storeService = storeSessaoVotacaoService,
            sessaoVotacaoValidator = sessaoVotacaoValidator,
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

        whenever(sessaoVotacaoValidator.validate(any())).thenReturn(true)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(true)

        val result = sessaoVotacaoService.open(openRequest)
        result shouldBe true
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

        whenever(sessaoVotacaoValidator.validate(any())).thenReturn(true)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(true)

        val result = sessaoVotacaoService.open(openRequest)

        verify(storeSessaoVotacaoService).open(expectedDefault)
        result shouldBe true
    }

    @Test
    fun `throws exception when validation fail`() {
        val openRequest = SessaoVotacao(
            pautaId = 1L,
            startDateTime = null,
            endDateTime = null
        )

        val expectedException = RuntimeException("Error")

        whenever(sessaoVotacaoValidator.validate(any())).thenThrow(expectedException)
        whenever(storeSessaoVotacaoService.open(any())).thenReturn(true)

        val exception = shouldThrow<RuntimeException> {
            sessaoVotacaoService.open(openRequest)
        }
        exception.message shouldBe expectedException.message

        verify(storeSessaoVotacaoService, never()).open(any())
    }
}
