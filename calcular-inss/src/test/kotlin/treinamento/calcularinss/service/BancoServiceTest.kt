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
import treinamento.calcularinss.fixture.banco.BancoFixture.Companion.getBanco
import treinamento.calcularinss.repository.BancoRepository
import java.util.*

@ExtendWith(MockitoExtension::class)
class BancoServiceTest {

    @InjectMocks
    private lateinit var bancoService: BancoService

    @Mock
    private lateinit var bancoRepository: BancoRepository

    @Test
    @DisplayName("salvar deve retornar o banco que foi salvo no banco de dados")
    fun salvar() {
        val banco = getBanco()

        `when`(bancoRepository.save(any())).thenReturn(banco)

        val resultado = bancoService.salvar(banco)

        assertNotNull(resultado)
        assertEquals(resultado, banco)
    }

    @Test
    @DisplayName("buscarPorId deve retornar o banco que foi encontrado pelo seu id")
    fun buscarPorId() {
        val banco = getBanco()

        `when`(bancoRepository.findById(anyInt())).thenReturn(Optional.of(banco))

        val resultado = bancoService.buscarPorId(1)

        assertNotNull(resultado)
        assertEquals(resultado, banco)
    }

    @Test
    @DisplayName("buscarPorId deve retornar uma exceção caso o banco não seja encontrado pelo seu id")
    fun buscarPorIdNaoEncontrado() {
        `when`(bancoRepository.findById(anyInt())).thenReturn(Optional.empty())

        assertThrows(NaoEncontradoException::class.java) {bancoService.buscarPorId(1)}
    }

    @Test
    @DisplayName("calcularInss deve retornar o valor do Inss com taxa de 10%, caso o salário recebido seja maior que o " +
            "salário mínimo e até o valor de 2000.0")
    fun calcularInss10() {
        val resultado = bancoService.calcularInss(1100.0)

        assertNotNull(resultado)
        assertEquals(resultado, 110.0)
    }


    @Test
    @DisplayName("calcularInss deve retornar o valor de Inss com taxa de 15%, caso o salário seja maior que 2000.0 até " +
            "3000.0")
    fun calcularInss15() {
        val resultado = bancoService.calcularInss(2001.0)

        assertNotNull(resultado)
        assertEquals(resultado, 300.15)
    }

    @Test
    @DisplayName("calcularInss deve retornar o valor de Inss com taxa de 20%, caso o salário seja maior que 3000.0")
    fun calcularInss20() {
        val resultado = bancoService.calcularInss(3001.0)

        assertNotNull(resultado)
        assertEquals(resultado, 600.2)
    }

    @Test
    @DisplayName("calcularInss deve retornar uma exceção caso o salário seja menor que o salário mínimo")
    fun calcularInssSalarioMenorMinimo() {
        assertThrows(IllegalArgumentException::class.java) {bancoService.calcularInss(1000.0)}
    }
}