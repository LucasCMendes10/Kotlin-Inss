package treinamento.calcularinss.entidade

import jakarta.persistence.*

@Entity
data class Pessoa (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var nome: String = "",
    var salario: Double = 0.0,
    @ManyToOne
    var banco: Banco? = null
) {
    data class Builder(
        var id: Int? = null,
        var nome: String = "",
        var salario: Double = 0.0,
        var banco: Banco? = null
    ) {
        fun id(id: Int?) = apply { this.id = id }
        fun nome(nome: String) = apply { this.nome = nome }
        fun salario(salario: Double) = apply { this.salario = salario }
        fun banco(banco: Banco?) = apply { this.banco = banco }
        fun builder() = Pessoa(id, nome, salario, banco)
    }
}