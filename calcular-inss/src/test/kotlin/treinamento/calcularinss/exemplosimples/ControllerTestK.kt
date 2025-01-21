package treinamento.calcularinss.exemplosimples

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [Controller::class])
class ControllerTestK {

    @MockBean
    private lateinit var calculadora: Calculadora

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `calcularInss deve retornar status 200 e o valor do Inss caso o salário seja igual ou maior ao salário mínimo`() {
        `when`(calculadora.calcularInss(any())).thenReturn(110.0)

        mockMvc.perform(get("/teste/1100.0"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").value("110.0"))
    }
}