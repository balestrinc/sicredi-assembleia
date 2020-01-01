package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.core.validation.VotoValidator
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VotoServiceTests {
    private lateinit var votoService: VotoService
    private val storeVotoService: StoreVotoService = mock()
    private val votoValidator: VotoValidator = mock()

    @BeforeEach
    fun beforeEach() {
        votoService = VotoService(
            storeVotoService = storeVotoService,
            validator = votoValidator
        )
    }

    @Test
    fun `saves vote when is valid`() {
        val voto = Voto(
            pautaId = 1L,
            sessaoId = 2L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        whenever(votoValidator.validate(voto)).thenReturn(true)
        whenever(storeVotoService.save(voto)).thenReturn(voto)

        val result = votoService.vote(voto)
        result shouldBe voto
    }

    @Test
    fun `throws exception when validation fail`() {
        val voto = Voto(
            pautaId = 1L,
            sessaoId = 2L,
            associadoCPF = "62289608068",
            votoOpcao = VotoOpcao.SIM
        )

        val expectedException = RuntimeException("Error")
        whenever(votoValidator.validate(voto)).thenThrow(expectedException)

        val exception = shouldThrow<RuntimeException> {
            votoService.vote(voto)
        }
        exception.message shouldBe expectedException.message

        verify(storeVotoService, never()).save(any())
    }
}
