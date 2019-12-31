package br.com.sicredi.assembleia.domain.model

enum class VotoOpcao {
    SIM,
    NAO
}

data class Voto(
    val id: Long = 0,
    val pautaId: Long,
    val sessaoId: Long,
    val associadoCPF: String,
    val votoOpcao: VotoOpcao
)
