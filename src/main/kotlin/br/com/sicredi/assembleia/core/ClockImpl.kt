package br.com.sicredi.assembleia.core

import java.time.LocalDateTime
import org.springframework.stereotype.Component

@Component
class ClockImpl : Clock {
    override fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}
