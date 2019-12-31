package br.com.sicredi.assembleia.repository.entity

import br.com.sicredi.assembleia.domain.model.VotoOpcao
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "voto")
data class VotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var pautaId: Long,
    var sessaoId: Long,

    @Enumerated(EnumType.STRING)
    var votoOpcao: VotoOpcao,

    var associadoCPF: String
)
