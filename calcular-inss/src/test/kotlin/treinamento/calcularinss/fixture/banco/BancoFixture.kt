package treinamento.calcularinss.fixture.banco

import treinamento.calcularinss.entidade.Banco

class BancoFixture {
    companion object {
        fun getBanco(): Banco {
            return Banco.Builder()
                .id(1)
                .nome("Banco do Brasil")
                .numeroAgencia(10)
                .build()
        }
    }
}