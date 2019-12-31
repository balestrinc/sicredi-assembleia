package br.com.sicredi.assembleia.support

import br.com.sicredi.assembleia.core.Clock
import java.time.LocalDateTime

class FakeClock(private val currentTime: LocalDateTime) : Clock {
    override fun now(): LocalDateTime {
        return currentTime
    }
}
