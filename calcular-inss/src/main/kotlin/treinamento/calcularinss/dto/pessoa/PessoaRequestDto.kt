package treinamento.calcularinss.dto.pessoa

import jakarta.validation.constraints.NotBlank

data class PessoaRequestDto (
    @field:NotBlank
    var nome: String,
    var salario: Double,
    var idBanco: Int
)