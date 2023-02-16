package br.api.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.api.forum.config.security.TokenService;
import br.api.forum.controller.dto.TokenDto;
import br.api.forum.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form){ //Método chamado quando for solicitado login do usuário. Parâmetros vem no corpo da requisição.
		UsernamePasswordAuthenticationToken dadosLogin = form.converter(); //Pega o email e senha passados sob parâmetro(através do form e seu método que retorna uma nova instancia da classe UsernamePasswordAuthenticationToken que terão os valores do JSON em email e senha) e passa para autenticação por token. 
		
		try {
			Authentication authenticate = authManager.authenticate(dadosLogin); //Autentica com os dados de usuario e senha do cliente(essa linha chama a classe @Service que faz a conexão com o DB e configurações).
			String token = tokenService.gerarToken(authenticate); //Geração do token.
			
			return ResponseEntity.ok(new TokenDto(token, "bearer")); //Devolve o token e tipo de autenticação no corpo e Retorna 200.
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build(); //Retorna bad request.
		}
		
	}
	
}
