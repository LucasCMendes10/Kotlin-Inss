package treinamento.calcularinss.fixture.banco

import treinamento.calcularinss.dto.banco.BancoRequestDto

class BancoRequestDtoFixture {
    companion object{
        fun getBancoRequestDto(): BancoRequestDto {
            return BancoRequestDto("Banco do Brasil", 10)
        }
    }
}