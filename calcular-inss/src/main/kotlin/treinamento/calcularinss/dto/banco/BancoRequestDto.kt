package treinamento.calcularinss.dto.banco

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class BancoRequestDto (
    @field:NotBlank
    var nome: String,

    @field:NotNull
    @field:Positive
    var numeroAgencia: Int?
)