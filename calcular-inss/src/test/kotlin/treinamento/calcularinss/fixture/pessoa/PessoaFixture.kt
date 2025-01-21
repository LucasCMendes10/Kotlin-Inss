package treinamento.calcularinss.fixture.pessoa

import treinamento.calcularinss.entidade.Pessoa
import treinamento.calcularinss.fixture.banco.BancoFixture

class PessoaFixture {
    companion object {
        fun getPessoa(): Pessoa {
            return Pessoa.Builder()
                .id(1)
                .nome("Lucas")
                .salario(1100.00)
                .banco(BancoFixture.getBanco())
                .builder()
        }
    }
}