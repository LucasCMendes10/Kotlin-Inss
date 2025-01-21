package treinamento.calcularinss.service;

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import treinamento.calcularinss.entidade.Banco
import treinamento.calcularinss.exception.NaoEncontradoException
import treinamento.calcularinss.repository.BancoRepository

@Service
class BancoService {

    @Autowired
    private lateinit var bancoRepository: BancoRepository

    val SALARIO_MINIMO: Double = 1100.0

    @Transactional
    fun salvar(banco: Banco): Banco {
        return bancoRepository.save(banco)
    }

    fun buscarPorId(id: Int): Banco {
        return bancoRepository.findById(id).orElseThrow{NaoEncontradoException()}
    }

    fun calcularInss(salario: Double): Double {
        if (salario < SALARIO_MINIMO) throw IllegalArgumentException("Salário não pode ser menor que o salário mínimo")

        var taxa = 0.20

        if (salario <= 2000) {
            taxa = 0.10
        } else if (salario <= 3000) {
            taxa = 0.15
        }

        return salario * taxa
    }
}