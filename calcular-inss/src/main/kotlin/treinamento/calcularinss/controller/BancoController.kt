package treinamento.calcularinss.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import treinamento.calcularinss.dto.banco.BancoMapper
import treinamento.calcularinss.dto.banco.BancoRequestDto
import treinamento.calcularinss.dto.banco.BancoResponseDto
import treinamento.calcularinss.dto.pessoa.PessoaMapper
import treinamento.calcularinss.dto.pessoa.PessoaResponseDto
import treinamento.calcularinss.service.BancoService

@RestController
@RequestMapping(BancoController.BASE_URL)
class BancoController {

    companion object {
        const val BASE_URL = "/bancos"
    }

    @Autowired
    private lateinit var bancoService: BancoService

    @PostMapping
    fun criar(@Valid @RequestBody dto: BancoRequestDto): ResponseEntity<BancoResponseDto> {
        return ResponseEntity.status(201).body(
            BancoMapper.toResponseDto(bancoService.salvar(BancoMapper.toEntity(dto)))
        )
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<BancoResponseDto> {
        return ResponseEntity.status(200).body(BancoMapper.toResponseDto(bancoService.buscarPorId(id)))
    }
}