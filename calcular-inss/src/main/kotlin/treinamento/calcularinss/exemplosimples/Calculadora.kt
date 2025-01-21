package treinamento.calcularinss.exemplosimples

class Calculadora {

    val SALARIO_MINIMO: Double = 1100.0

    fun calcularInss(salario: Double?): Double {
        if (salario == null) throw IllegalArgumentException("Salário não pode ser nulo")
        if (salario < SALARIO_MINIMO) throw IllegalArgumentException("Salário não pode ser menor que o salário mínimo")

        var taxa = 0.20

        if (salario <= 2000) {
            taxa = 0.10
        } else if (salario <= 3000) {
            taxa = 0.15
        }

        return salario * taxa
    }
}