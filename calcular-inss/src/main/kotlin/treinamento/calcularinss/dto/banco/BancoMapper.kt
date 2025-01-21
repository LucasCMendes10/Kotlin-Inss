package treinamento.calcularinss.dto.banco

import treinamento.calcularinss.entidade.Banco
import treinamento.calcularinss.exception.RequisicaoRuimException

class BancoMapper {
    companion object {
        fun toEntity(dto: BancoRequestDto): Banco {
            return Banco.Builder()
                .nome(dto.nome)
                .numeroAgencia(dto.numeroAgencia ?: throw RequisicaoRuimException())
                .build()
        }

        fun toResponseDto(entidade: Banco): BancoResponseDto {
            return BancoResponseDto.Builder()
                .id(entidade.id ?: throw RequisicaoRuimException())
                .nome(entidade.nome)
                .numeroAgencia(entidade.numeroAgencia)
                .build()
        }
    }
}