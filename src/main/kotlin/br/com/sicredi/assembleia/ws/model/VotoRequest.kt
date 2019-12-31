package br.com.sicredi.assembleia.ws.model

data class VotoRequest(
    val associadoCPF: String,
    val votoOpcao: String
)
