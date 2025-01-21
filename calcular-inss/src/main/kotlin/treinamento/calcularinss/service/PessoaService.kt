package treinamento.calcularinss.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import treinamento.calcularinss.entidade.Pessoa
import treinamento.calcularinss.exception.NaoEncontradoException
import treinamento.calcularinss.exception.RequisicaoRuimException
import treinamento.calcularinss.repository.PessoaRepository

@Service
class PessoaService {

    @Autowired
    private lateinit var pessoaRepository: PessoaRepository

    @Autowired
    private lateinit var bancoService: BancoService

    @Transactional
    fun salvar(pessoa: Pessoa, idBanco: Int): Pessoa {
        pessoa.banco = bancoService.buscarPorId(idBanco)
        return pessoaRepository.save(pessoa)
    }

    fun buscarPorId(id: Int): Pessoa {
        return pessoaRepository.findById(id).orElseThrow{NaoEncontradoException()}
    }

    fun atualizar(id: Int, pessoa: Pessoa): Pessoa {
        if (!existePeloId(id)) throw NaoEncontradoException()
        pessoa.id = id
        return salvar(pessoa, pessoa.banco?.id ?: throw RequisicaoRuimException())
    }

    fun existePeloId(id: Int): Boolean {
        return pessoaRepository.existsById(id)
    }

    fun pagarInss(idPessoa: Int): Pessoa {
        val pessoa = buscarPorId(idPessoa)
        pessoa.salario -= bancoService.calcularInss(pessoa.salario)
        return atualizar(idPessoa, pessoa)
    }
}