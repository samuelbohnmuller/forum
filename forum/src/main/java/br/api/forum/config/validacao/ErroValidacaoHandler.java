package br.api.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Anotação de tratamento de erro REST.
public class ErroValidacaoHandler {

	@Autowired
	private MessageSource messageSource; //Atributo para mensagens em vários idiomas.
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //Mostra status 400.
	@ExceptionHandler(MethodArgumentNotValidException.class) //Quando ocorrer excessão do tipo informações REST inválidas, chamará esse método.
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) { //Exception encontrada será passada sob parâmetro.
		List<ErroDeFormularioDto> erroFormulario = new ArrayList<>();
		
		List<FieldError> fieldError = exception.getBindingResult().getFieldErrors(); //Erros de formulário encontrados.
		fieldError.forEach(e -> { //laço na lista de erros
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); //Pego a mensagem do erro na localidade(Brasil).
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem); //Passando o campo do erro e a mensagem de erro de fieldError para classe que contém atributos para tais.
			erroFormulario.add(erro);
		});
		
		return erroFormulario; //Retorna a lista de erros de formulário na requisição com o erro e mensagem de erro.
	}
	
}
