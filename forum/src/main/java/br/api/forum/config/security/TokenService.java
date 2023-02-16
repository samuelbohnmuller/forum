package br.api.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.api.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService { //Classe responsável por gera o token.

	@Value("${forum.jwt.expiration}") //Importo propriedade do aplication.properties para valor de expiração do token.
	private String expiration; 
	
	@Value("${forum.jwt.secret}") //senha do token puxado do aplication.properties.
	private String secret; 
	
	public String gerarToken(Authentication authentication) { //Gera o token.
		Usuario logado = (Usuario) authentication.getPrincipal(); //Pega o Usuário logado(usado cast pra converter pra Usuario).
		Date hoje = new Date(); //Data do token deve ser em Date.
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration)); //Pega o milisegundos da variável hoje e soma com a variável de tempo de expiração do token(1 dia). Necessário converter expiration String para Long.
		
		return Jwts.builder()
				.setIssuer("API forum") //Nome da API.
				.setSubject(logado.getId().toString()) //Pega o Id do usuário(converte pra String).
				.setIssuedAt(hoje) //Data da geração do token.
				.setExpiration(dataExpiracao) //Tempo de expiração do token.
				.signWith(SignatureAlgorithm.HS256, secret)//criptografia e senha.
				.compact(); //Compacta a senha e transforma em String.
	}

	public boolean TokenValido(String token) { //Descriptografa o token e confere se está o passando a senha do aplication.properties e recuperando as informações dentro do token.
		try {
		     Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
		     return true; //Se retornar o objeto.
		 } catch (Exception e) {
		     return false;
		 }
	}
	
	public Long getIdUsuario(String token) {
	    Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	    return Long.parseLong(claims.getSubject()); //Retorna o id do usuário pego no token acima(convertendo para long).
	}

}
