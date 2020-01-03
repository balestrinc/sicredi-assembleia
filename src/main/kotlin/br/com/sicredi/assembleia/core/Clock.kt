package br.com.sicredi.assembleia.core

import java.time.LocalDateTime

interface Clock {
    fun now(): LocalDateTime
}
