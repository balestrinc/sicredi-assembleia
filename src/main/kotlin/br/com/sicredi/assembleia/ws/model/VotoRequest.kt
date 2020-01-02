package br.com.sicredi.assembleia.ws.model

import org.hibernate.validator.constraints.br.CPF

data class VotoRequest(
    @field:CPF(message = "CPF inválido")
    val associadoCPF: String,
    val votoOpcao: String
)
