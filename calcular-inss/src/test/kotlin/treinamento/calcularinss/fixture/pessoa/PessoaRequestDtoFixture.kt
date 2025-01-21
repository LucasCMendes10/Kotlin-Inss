package treinamento.calcularinss.fixture.pessoa

import treinamento.calcularinss.dto.pessoa.PessoaRequestDto

class PessoaRequestDtoFixture {
    companion object {
        fun getPessoaRequestDto() : PessoaRequestDto {
            return PessoaRequestDto("Lucas", 1100.0, 1)
        }
    }
}