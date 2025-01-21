package treinamento.calcularinss.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import treinamento.calcularinss.exception.NaoEncontradoException
import treinamento.calcularinss.fixture.banco.BancoFixture.Companion.getBanco
import treinamento.calcularinss.fixture.banco.BancoRequestDtoFixture.Companion.getBancoRequestDto
import treinamento.calcularinss.service.BancoService

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [BancoController::class])
class BancoControllerTest {

    @MockBean
    private lateinit var bancoService: BancoService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("criar deve retornar status 201 com dados do banco que foi cadastrado, possuindo valores válidos")
    fun criar() {
        val banco = getBanco()

        `when`(bancoService.salvar(any())).thenReturn(banco)

        mockMvc.perform(
            post(BancoController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getBancoRequestDto()))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.nome").value("Banco do Brasil"))
            .andExpect(jsonPath("$.numeroAgencia").value("10"))
    }

    @Test
    @DisplayName("criar deve retornar 400 caso o corpo de resposta seja nulo")
    fun criarCorpoNulo() {
        mockMvc.perform(post(BancoController.BASE_URL))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @DisplayName("criar deve retornar 400 caso o banco que o usuário deseja cadastrar tenha o nome vazio")
    fun criarNomeVazio() {
        val dto = getBancoRequestDto()
        dto.nome = ""

        mockMvc.perform(
            post(BancoController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @DisplayName("criar deve retornar 400 caso o banco que o usuário deseja cadastrar tenha o nome em branco")
    fun criarNomeEmBranco() {
        val dto = getBancoRequestDto()
        dto.nome = "   "

        mockMvc.perform(
            post(BancoController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @DisplayName("criar deve retornar 400 caso o banco que o usuário deseja cadastrar tenha o número da agência nulo")
    fun criarNumeroAgenciaNulo() {
        val dto = getBancoRequestDto()
        dto.numeroAgencia = null

        mockMvc.perform(
            post(BancoController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @DisplayName("criar deve retornar 400 caso o banco que o usuário deseja cadastrar tenha o número da agência igual 0")
    fun criarNumeroAgenciaZero() {
        val dto = getBancoRequestDto()
        dto.numeroAgencia = 0

        mockMvc.perform(
            post(BancoController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @DisplayName(
        "criar deve retornar 400 caso o banco que o usuário deseja cadastrar tenha o número da agência menor " +
                "que 0"
    )
    fun criarNumeroAgenciaNegativo() {
        val dto = getBancoRequestDto()
        dto.numeroAgencia = -1

        mockMvc.perform(
            post(BancoController.BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @DisplayName("buscarPorId deve retornar status 200 com dados do banco que foi encontrado pelo id")
    fun buscarPorId() {
        val banco = getBanco()

        `when`(bancoService.buscarPorId(any())).thenReturn(banco)

        mockMvc.perform(get(BancoController.BASE_URL + "/" + 1))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").isNotEmpty)
            .andExpect(jsonPath("$.nome").isNotEmpty)
            .andExpect(jsonPath("$.numeroAgencia").isNotEmpty)
    }

    @Test
    @DisplayName("buscarPorId deve retornar status 404 caso o banco não seja encontrado pelo id")
    fun buscarPorIdNaoEncontrado() {
        `when`(bancoService.buscarPorId(any())).thenThrow(NaoEncontradoException::class.java)

        mockMvc.perform(get(BancoController.BASE_URL + "/" + 1))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$").doesNotExist())
    }
}