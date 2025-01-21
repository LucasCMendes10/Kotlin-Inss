package treinamento.calcularinss.entidade

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Banco (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var nome: String = "",
    var numeroAgencia: Int = 0
) {
    data class Builder(
        var id: Int? = null,
        var nome: String = "",
        var numeroAgencia: Int = 0
    ) {
        fun id(id: Int?) = apply { this.id = id }
        fun nome(nome: String) = apply { this.nome = nome }
        fun numeroAgencia(numeroAgencia: Int) = apply { this.numeroAgencia = numeroAgencia }
        fun build() = Banco(id, nome, numeroAgencia)
    }
}