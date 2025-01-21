package treinamento.calcularinss.exemplosimples

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/teste")
class Controller {

    private var calculadora: Calculadora = Calculadora()

    @GetMapping("/{salario}")
    fun calcularInss(@PathVariable salario: Double?): ResponseEntity<Double> {
        return ResponseEntity.status(200).body(calculadora.calcularInss(salario))
    }
}