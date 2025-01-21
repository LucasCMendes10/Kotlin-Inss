package treinamento.calcularinss.dto.pessoa

data class PessoaResponseDto (
    var id: Int?,
    var nome: String,
    var salario: Double,
    var banco: BancoResponse
) {
    data class Builder(
        var id: Int? = null,
        var nome: String = "",
        var salario: Double = 0.0,
        var banco: BancoResponse =  BancoResponse()
    ) {
        fun id(id: Int?) = apply { this.id = id }
        fun nome(nome: String) = apply { this.nome = nome }
        fun salario(salario: Double) = apply { this.salario = salario }
        fun banco(banco: BancoResponse) = apply { this.banco = banco }
        fun build() = PessoaResponseDto(id, nome, salario, banco)
    }

    data class BancoResponse (
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
            fun build() = BancoResponse(id, nome, numeroAgencia)
        }
    }
}