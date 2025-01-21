package treinamento.calcularinss.exemplosimples.javat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import treinamento.calcularinss.exemplosimples.Calculadora;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes em Java")
public class CalculadoraTestJ {

    private final Calculadora calculadora = new Calculadora();

    @Test
    @DisplayName("Com o salário de R$ 1.000,00 deve retornar uma exceção por estar abaixo do salário mínimo")
    void teste1() {
        Double salario = 1000.0;

        assertNotNull(salario);
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> calculadora.calcularInss(salario));
        assertEquals("Salário não pode ser menor que o salário mínimo", error.getMessage());
    }

    @Test
    @DisplayName("Com o salário nulo deve retornar uma exceção")
    void teste2() {
        Double salario = null;

        assertNull(salario);
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> calculadora.calcularInss(salario));
        assertEquals("Salário não pode ser nulo", error.getMessage());
    }

    @Test
    @DisplayName("Com o salário de R$ 1.100,00 deve retornar o valor R$ 110,00")
    void teste3() {
        Double salario = 1100.0;

        Double resultado = calculadora.calcularInss(salario);

        assertNotNull(resultado);
        assertEquals(110.0, resultado);
    }

    @Test
    @DisplayName("Com o salário de R$ 2.001,00 deve retornar o valor R$ 300,15")
    void teste4() {
        Double salario = 2001.0;

        Double resultado = calculadora.calcularInss(salario);

        assertNotNull(resultado);
        assertEquals(300.15, resultado);
    }

    @Test
    @DisplayName("Com o salário de R$ 3.001,00 deve retornar o valor R$ 600,20")
    void teste5() {
        Double salario = 3001.0;

        Double resultado = calculadora.calcularInss(salario);

        assertNotNull(resultado);
        assertEquals(600.2, resultado);
    }
}
