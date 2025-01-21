package treinamento.calcularinss.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import treinamento.calcularinss.dto.pessoa.PessoaMapper
import treinamento.calcularinss.dto.pessoa.PessoaRequestDto
import treinamento.calcularinss.dto.pessoa.PessoaResponseDto
import treinamento.calcularinss.service.PessoaService

@RestController
@RequestMapping(PessoaController.BASE_URL)
class PessoaController {

    companion object {
        const val BASE_URL = "/pessoas"
    }

    @Autowired
    private lateinit var pessoaService: PessoaService

    @PostMapping
    fun criar(@Valid @RequestBody dto: PessoaRequestDto): ResponseEntity<PessoaResponseDto> {
        return ResponseEntity.status(201).body(
            PessoaMapper.toResponseDto(pessoaService.salvar(PessoaMapper.toEntity(dto), dto.idBanco))
        )
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<PessoaResponseDto> {
        return ResponseEntity.status(200).body(PessoaMapper.toResponseDto(pessoaService.buscarPorId(id)))
    }

    @PatchMapping("/inss/{id}")
    fun pagarInss(@PathVariable id: Int): ResponseEntity<PessoaResponseDto> {
        return ResponseEntity.status(200).body(PessoaMapper.toResponseDto(pessoaService.pagarInss(id)))
    }
}