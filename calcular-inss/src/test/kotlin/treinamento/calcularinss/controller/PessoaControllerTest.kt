package treinamento.calcularinss.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import treinamento.calcularinss.exception.NaoEncontradoException
import treinamento.calcularinss.exception.RequisicaoRuimException
import treinamento.calcularinss.fixture.pessoa.PessoaFixture.Companion.getPessoa
import treinamento.calcularinss.fixture.pessoa.PessoaRequestDtoFixture.Companion.getPessoaRequestDto
import treinamento.calcularinss.service.PessoaService

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [PessoaController::class])
class PessoaControllerTest {

    @MockBean
    private lateinit var pessoaService: PessoaService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Nested
    @DisplayName("Criar")
    inner class Criar {
        @Nested
        @DisplayName("Cenários de 201")
        inner class Status201 {
            @Test
            @DisplayName(
                "criar deve retornar 201 com os dados da pessoa que foi cadastrada, caso o banco tenha sido encontrado " +
                        "e todos os dados da pessoa estejam válidos"
            )
            fun criar() {
                val pessoa = getPessoa()

                `when`(pessoaService.salvar(any(), any())).thenReturn(pessoa)

                mockMvc.perform(
                    post(PessoaController.BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPessoaRequestDto()))
                )
                    .andExpect(status().isCreated)
                    .andExpect(jsonPath("$.id").value("1"))
                    .andExpect(jsonPath("$.nome").value("Lucas"))
                    .andExpect(jsonPath("$.salario").value("1100.0"))
                    .andExpect(jsonPath("$.banco").isNotEmpty)
                    .andExpect(jsonPath("$.banco.id").value("1"))
                    .andExpect(jsonPath("$.banco.nome").value("Banco do Brasil"))
                    .andExpect(jsonPath("$.banco.numeroAgencia").value("10"))
            }
        }

        @Nested
        @DisplayName("Cenários de 400")
        inner class Status400 {
            @Test
            @DisplayName("criar deve retornar 400 caso a pessoa que o usuário tente cadastrar possua o nome vazio")
            fun criarNomeVazio() {
                val dto = getPessoaRequestDto()
                dto.nome = ""

                mockMvc.perform(
                    post(PessoaController.BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$").doesNotExist())
            }

            @Test
            @DisplayName("criar deve retornar 400 caso a pessoa que o usuário tente cadastrar possua o nome em branco")
            fun criarNomeEmBranco() {
                val dto = getPessoaRequestDto()
                dto.nome = "   "

                mockMvc.perform(
                    post(PessoaController.BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$").doesNotExist())
            }
        }

        @Nested
        @DisplayName("Cenários de 404")
        inner class Status404 {
            @Test
            @DisplayName(
                "criar deve retornar 404 caso o banco associado na pessoa que o usuário tente cadastrar não " +
                        "seja encontrado pelo id"
            )
            fun criarBancoNaoEncontrado() {
                `when`(pessoaService.salvar(any(), any())).thenThrow(NaoEncontradoException())

                mockMvc.perform(
                    post(PessoaController.BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPessoaRequestDto()))
                )
                    .andExpect(status().isNotFound)
                    .andExpect(jsonPath("$").doesNotExist())
            }
        }
    }

    @Nested
    @DisplayName("Buscar por id")
    inner class BuscarPorId {

        @Nested
        @DisplayName("Cenários de 200")
        inner class Status200 {
            @Test
            @DisplayName("buscarPorId deve retornar 200 com os dados da pessoa de dados que foi encontrada pelo id")
            fun buscarPorId() {
                val pessoa = getPessoa()

                `when`(pessoaService.buscarPorId(any())).thenReturn(pessoa)

                mockMvc.perform(get(PessoaController.BASE_URL + "/" + 1))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").isNotEmpty)
                    .andExpect(jsonPath("$.nome").isNotEmpty)
                    .andExpect(jsonPath("$.salario").isNotEmpty)
                    .andExpect(jsonPath("$.banco").isNotEmpty)
                    .andExpect(jsonPath("$.banco.id").isNotEmpty)
                    .andExpect(jsonPath("$.banco.nome").isNotEmpty)
                    .andExpect(jsonPath("$.banco.numeroAgencia").isNotEmpty)
            }
        }

        @Nested
        @DisplayName("Cenário de 404")
        inner class Status404 {
            @Test
            @DisplayName("buscarPorId deve retornar 404 caso a pessoa não seja encontrada pelo id")
            fun buscarPorIdNaoEncontrado() {
                `when`(pessoaService.buscarPorId(any())).thenThrow(NaoEncontradoException())


                mockMvc.perform(get(PessoaController.BASE_URL + "/" + 1))
                    .andExpect(status().isNotFound)
                    .andExpect(jsonPath("$").doesNotExist())
            }
        }
    }

    @Nested
    @DisplayName("Pagar Inss")
    inner class PagarInss {

        @Nested
        @DisplayName("Cenários de 200")
        inner class Status200 {
            @Test
            @DisplayName("pagarInss deve retornar 200 caso a pessoa tenha todos os dados corretos para pagar o Inss")
            fun pagarInss() {
                val pessoaFinal = getPessoa()
                pessoaFinal.salario = 990.0

                `when`(pessoaService.pagarInss(any())).thenReturn(pessoaFinal)

                mockMvc.perform(patch(PessoaController.BASE_URL + "/inss/" + 1))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").isNotEmpty)
                    .andExpect(jsonPath("$.nome").isNotEmpty)
                    .andExpect(jsonPath("$.salario").value("990.0"))
                    .andExpect(jsonPath("$.banco").isNotEmpty)
                    .andExpect(jsonPath("$.banco.id").isNotEmpty)
                    .andExpect(jsonPath("$.banco.nome").isNotEmpty)
                    .andExpect(jsonPath("$.banco.numeroAgencia").isNotEmpty)
            }
        }

        @Nested
        @DisplayName("Cenários de 400")
        inner class Status400 {
            @Test
            @DisplayName("pagarInss deve retornar 400 caso a pessoa o dado do banco nulo")
            fun pagarInssBancoNulo() {
                `when`(pessoaService.pagarInss(any())).thenThrow(RequisicaoRuimException())

                mockMvc.perform(patch(PessoaController.BASE_URL + "/inss/" + 1))
                    .andExpect(status().isBadRequest)
                    .andExpect(jsonPath("$").doesNotExist())
            }
        }

        @Nested
        @DisplayName("Cenários de 404")
        inner class Status404 {
            @Test
            @DisplayName("pagarInss deve retornar 404 caso a pessoa não seja encontrada pelo id")
            fun pagarInssPessoaNaoEncontrada() {
                `when`(pessoaService.pagarInss(any())).thenThrow(NaoEncontradoException())

                mockMvc.perform(patch(PessoaController.BASE_URL + "/inss/" + 1))
                    .andExpect(status().isNotFound)
                    .andExpect(jsonPath("$").doesNotExist())
            }

            @Test
            @DisplayName("pagarInss deve retornar 404 caso o banco da pessoa não seja encontrado pelo id")
            fun pagarInssBancoNaoEncontrado() {
                `when`(pessoaService.pagarInss(any())).thenThrow(NaoEncontradoException())

                mockMvc.perform(patch(PessoaController.BASE_URL + "/inss/" + 1))
                    .andExpect(status().isNotFound)
                    .andExpect(jsonPath("$").doesNotExist())
            }
        }
    }
}