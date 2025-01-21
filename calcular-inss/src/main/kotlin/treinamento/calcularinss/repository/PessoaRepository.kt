package treinamento.calcularinss.repository

import org.springframework.data.jpa.repository.JpaRepository
import treinamento.calcularinss.entidade.Pessoa

interface PessoaRepository: JpaRepository<Pessoa, Int> {
}