package treinamento.calcularinss.repository

import org.springframework.data.jpa.repository.JpaRepository
import treinamento.calcularinss.entidade.Banco

interface BancoRepository: JpaRepository<Banco, Int> {
}