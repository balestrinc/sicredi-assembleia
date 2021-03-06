package br.com.sicredi.assembleia.support

import br.com.sicredi.assembleia.core.Clock
import java.time.LocalDateTime
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestConfiguration {
    @Bean
    fun clock(): Clock {
        val currentTime = LocalDateTime.parse(TestConstants.CURRENT_TIME)
        return FakeClock(currentTime)
    }
}
