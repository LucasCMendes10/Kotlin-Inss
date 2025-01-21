package treinamento.calcularinss.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class NaoEncontradoException: RuntimeException("Recurso não encontrado")
