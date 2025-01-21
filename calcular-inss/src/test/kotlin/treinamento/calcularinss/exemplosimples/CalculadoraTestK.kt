package treinamento.calcularinss.exemplosimples

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("Testes em Kotlin")
class CalculadoraTestK {

    private val calculadora: Calculadora = Calculadora()

    @Test
    @DisplayName("Com o salário de R$ 1.000,00 deve retornar uma exceção por estar abaixo do salário mínimo")
    fun teste1() {
        val salario = 1000.0

        assertNotNull(salario)
        val error = assertThrows(IllegalArgumentException::class.java) { calculadora.calcularInss(salario) }
        assertEquals("Salário não pode ser menor que o salário mínimo", error.message)
    }

    @Test
    @DisplayName("Com o salário nulo deve retornar uma exceção")
    fun teste2() {
        val salario = null

        assertNull(salario)
        val error = assertThrows(IllegalArgumentException::class.java) {calculadora.calcularInss(salario)}
        assertEquals("Salário não pode ser nulo", error.message)
    }

    @Test
    @DisplayName("Com o salário de R$ 1.100,00 deve retornar o valor R$ 110,00")
    fun teste3() {
        val salario = 1100.0

        val resultado = calculadora.calcularInss(salario);

        assertNotNull(resultado)
        assertEquals(110.0, resultado)
    }

    @Test
    @DisplayName("Com o salário de R$ 2.001,00 deve retornar o valor R$ 300,15")
    fun teste4() {
        val salario = 2001.0

        val resultado = calculadora.calcularInss(salario)

        assertNotNull(resultado)
        assertEquals(300.15, resultado)
    }

    @Test
    @DisplayName("Com o salário de R$ 3.001,00 deve retornar o valor R$ 600,20")
    fun teste5() {
        val salario = 3001.0

        val resultado = calculadora.calcularInss(salario)

        assertNotNull(resultado)
        assertEquals(600.2, resultado)
    }
}