package treinamento.calcularinss.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import treinamento.calcularinss.exception.NaoEncontradoException
import treinamento.calcularinss.exception.RequisicaoRuimException
import treinamento.calcularinss.fixture.banco.BancoFixture.Companion.getBanco
import treinamento.calcularinss.fixture.pessoa.PessoaFixture.Companion.getPessoa
import treinamento.calcularinss.repository.PessoaRepository
import java.util.*

@ExtendWith(MockitoExtension::class)
class PessoaServiceTest {

    @InjectMocks
    private lateinit var pessoaService: PessoaService

    @Mock
    private lateinit var pessoaRepository: PessoaRepository

    @Mock
    private lateinit var bancoService: BancoService

    @Test
    @DisplayName("salvar deve cadastrar uma pessoa no banco de dados, retornando ela, caso o banco dela seja encontrado")
    fun salvar() {
        val pessoa = getPessoa()
        val banco = getBanco()

        `when`(bancoService.buscarPorId(anyInt())).thenReturn(banco)
        `when`(pessoaRepository.save(any())).thenReturn(pessoa)

        val resultado = pessoaService.salvar(pessoa, 1)

        assertNotNull(resultado)
        assertEquals(pessoa, resultado)
    }

    @Test
    @DisplayName("salvar deve retornar uma exceção de não encontrado caso o banco da pessoa não exista pelo id")
    fun salvarBancoNaoEncontrado() {
        val pessoa = getPessoa()

        `when`(bancoService.buscarPorId(anyInt())).thenThrow(NaoEncontradoException())

        assertThrows(NaoEncontradoException::class.java) {pessoaService.salvar(pessoa, 1)}
    }

    @Test
    @DisplayName("buscarPorId deve retornar uma pessoa ao encontrar ela pelo id no banco")
    fun buscarPorId() {
        val pessoa = getPessoa()

        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoa))

        val resultado = pessoaService.buscarPorId(1)

        assertNotNull(resultado)
        assertEquals(resultado, pessoa)
    }

    @Test
    @DisplayName("buscarPorId deve retornar uma exceção ao não encontrar a pessoa pelo id no banco")
    fun buscarPorIdNaoEncontrado() {
        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.empty())

        assertThrows(NaoEncontradoException::class.java) {pessoaService.buscarPorId(1)}
    }

    @Test
    @DisplayName("atualizar deve retornar uma pessoa que teve seus dados atualizados no banco de dados, se ela existir " +
            "pelo id")
    fun atualizar() {
        val pessoa = getPessoa()

        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)
        `when`(pessoaRepository.save(any())).thenReturn(pessoa)

        val resultado = pessoaService.atualizar(1, pessoa)

        assertNotNull(resultado)
        assertEquals(pessoa, resultado)
    }

    @Test
    @DisplayName("atualizar deve retornar uma exceção caso a pessoa não exista pelo id")
    fun atualizarNaoExiste() {
        `when`(pessoaRepository.existsById(anyInt())).thenReturn(false)

        assertThrows(NaoEncontradoException::class.java) {pessoaService.atualizar(1, getPessoa())}
    }

    @Test
    @DisplayName("atualizar deve retornar uma exceção caso o id do banco da pessoa seja nulo")
    fun atualizarIdBancoNulo() {
        val pessoa = getPessoa()
        pessoa.banco?.id = null

        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)

        assertThrows(RequisicaoRuimException::class.java) {pessoaService.atualizar(1, pessoa)}
    }

    @Test
    @DisplayName("atualizar deve retornar uma exceção caso o banco da pessoa seja nulo")
    fun atualizarBancoNulo() {
        val pessoa = getPessoa()
        pessoa.banco = null

        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)

        assertThrows(RequisicaoRuimException::class.java) {pessoaService.atualizar(1, pessoa)}
    }

    @Test
    @DisplayName("existePeloId deve retornar true caso o id da pessoa seja encontrado no banco de dados")
    fun existePeloId() {
        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)

        val resultado = pessoaService.existePeloId(1)

        assertNotNull(resultado)
        assertTrue(resultado)
    }

    @Test
    @DisplayName("existePeloId deve retornar false caso o id da pessoa não seja encontrado no banco de dados")
    fun naoExistePeloId() {
        `when`(pessoaRepository.existsById(anyInt())).thenReturn(false)

        val resultado = pessoaService.existePeloId(1)

        assertNotNull(resultado)
        assertFalse(resultado)
    }

    @Test
    @DisplayName("pagarInss deve retornar uma pessoa que teve seu salário modificado por ter realizado o pagamento, por " +
            "tanto que seu salário seja maior que o salário mínimo e ela exista pelo seu id")
    fun pagarInss() {
        val pessoa = getPessoa()
        val pessoaFinal = getPessoa()
        pessoaFinal.salario = 990.0

        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoa))
        `when`(bancoService.calcularInss(anyDouble())).thenReturn(110.0)
        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)
        `when`(pessoaRepository.save(any())).thenReturn(pessoaFinal)

        val resultado = pessoaService.pagarInss(1)

        assertNotNull(resultado)
        assertEquals(resultado, pessoaFinal)
    }

    @Test
    @DisplayName("pagarInss deve retornar uma exceção caso a pessoa não seja encontrada pelo id")
    fun pagarInssPessoaNaoEncontrada() {
        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.empty())

        assertThrows(NaoEncontradoException::class.java) {pessoaService.pagarInss(1)}
    }

    @Test
    @DisplayName("pagarInss deve retornar uma exceção caso o salário da pessoa seja abaixo do salário mínimo")
    fun pagarInssSalarioAbaixoMinimo() {
        val pessoa = getPessoa()
        pessoa.salario = 500.0

        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoa))
        `when`(bancoService.calcularInss(anyDouble())).thenThrow(IllegalArgumentException())

        assertThrows(IllegalArgumentException::class.java) {pessoaService.pagarInss(1)}
    }

    @Test
    @DisplayName("pagarInss deve retornar uma exceção caso o banco da pessoa seja nulo")
    fun pagarInssBancoNulo() {
        val pessoa = getPessoa()
        pessoa.banco = null

        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoa))
        `when`(bancoService.calcularInss(anyDouble())).thenReturn(110.0)
        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)

        assertThrows(RequisicaoRuimException::class.java) {pessoaService.pagarInss(1)}
    }

    @Test
    @DisplayName("pagarInss deve retornar uma exceção caso o id do banco da pessoa seja nulo")
    fun pagarInssIdBancoNulo() {
        val pessoa = getPessoa()
        pessoa.banco?.id = null

        `when`(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoa))
        `when`(bancoService.calcularInss(anyDouble())).thenReturn(110.0)
        `when`(pessoaRepository.existsById(anyInt())).thenReturn(true)

        assertThrows(RequisicaoRuimException::class.java) {pessoaService.pagarInss(1)}
    }
}