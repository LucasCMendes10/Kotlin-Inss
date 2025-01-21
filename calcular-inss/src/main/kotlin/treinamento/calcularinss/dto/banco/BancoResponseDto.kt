package treinamento.calcularinss.dto.banco

data class BancoResponseDto (
    var id: Int,
    var nome: String,
    var numeroAgencia: Int
) {
    data class Builder (
        var id: Int = 0,
        var nome: String = "",
        var numeroAgencia: Int = 0
    ) {
        fun id(id: Int) = apply { this.id = id }
        fun nome(nome: String) = apply { this.nome = nome }
        fun numeroAgencia(numeroAgencia: Int) = apply { this.numeroAgencia = numeroAgencia }
        fun build() = BancoResponseDto(id, nome, numeroAgencia)
    }
}