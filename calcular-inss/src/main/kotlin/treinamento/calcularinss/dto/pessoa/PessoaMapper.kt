package treinamento.calcularinss.dto.pessoa

import treinamento.calcularinss.entidade.Pessoa
import treinamento.calcularinss.exception.RequisicaoRuimException

class PessoaMapper {
    companion object {
        fun toEntity(dto: PessoaRequestDto): Pessoa {
            return Pessoa.Builder()
                .nome(dto.nome)
                .salario(dto.salario)
                .builder()
        }

        fun toResponseDto(entidade: Pessoa): PessoaResponseDto {
            val banco = entidade.banco?.let {
                PessoaResponseDto.BancoResponse.Builder()
                    .id(it.id)
                    .nome(it.nome)
                    .numeroAgencia(it.numeroAgencia)
                    .build()
            }

            return PessoaResponseDto.Builder()
                .id(entidade.id)
                .nome(entidade.nome)
                .salario(entidade.salario)
                .banco(banco ?: throw RequisicaoRuimException())
                .build()
        }
    }
}