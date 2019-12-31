package br.com.sicredi.assembleia.api.converter

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.ws.model.VotoRequest

fun wsToDomainVoto(pautaId: Long, sessaoId: Long, request: VotoRequest): Voto {
    return Voto(
        pautaId = pautaId,
        sessaoId = sessaoId,
        associadoCPF = request.associadoCPF,
        votoOpcao = parseVotoOpcao(request.votoOpcao)
    )
}

private fun parseVotoOpcao(votoOpcao: String): VotoOpcao {
    val isVoteSim = listOf("SIM", "S", "YES").contains(votoOpcao.toUpperCase())
    return if (isVoteSim) VotoOpcao.SIM else VotoOpcao.NAO
}
