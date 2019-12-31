package br.com.sicredi.assembleia.domain.model

import java.time.LocalDateTime

data class SessaoVotacao(
    val id: Long = 0,
    val pautaId: Long,
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?
)
//
// class SessaoVotacaoResultado (
//     val sessaoVotacao: SessaoVotacao,
//     val totalVotos: Long,
//     val votosFavoraveis: Long,
//     val aprovado: Boolean
// )
//
// import org.hibernate.validator.constraints.br.CPF
// data class Associado(
//     val cpf: CPF
// )
//
// enum class Voto (val voto: Boolean){
//     SIM(true),
//     NAO(false)
// }
//
// data class SessaoVoto(
//     val pauta: Pauta,
//     val associado: Associado,
//     val voto: Voto
// )
