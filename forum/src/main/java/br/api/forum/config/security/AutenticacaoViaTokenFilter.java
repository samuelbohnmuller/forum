package br.api.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.api.forum.modelo.Usuario;
import br.api.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{

	private UsuarioRepository usuarioRepository;
	
	private TokenService tokenService; //Não é possível injeção de dependencia no atributo, então passarei o mesmo no construtor da classe.
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) { //Como não posso injetar dependencia em filtro, injeto no construtor.
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) //Filtro chamado a cada requisição.
			throws ServletException, IOException {
		String token = recuperaToken(request); //Recupera o token do cabeçalho.
		boolean valido = tokenService.TokenValido(token);
		
		if (valido) {
	        autenticarCliente(token);
	    }
		
		filterChain.doFilter(request, response); //segue o fluxo de requisição.

		
	}
	
	private String recuperaToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); //Pega o cabeçalho que é de nome Authorization(padrão).ated method stub
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer")) { //Se o token enviado pelo usuário for vazio, nulo ou se não começa com bearer.
			 return null;
		}
		
		return token.substring(7, token.length()); //Se não, retorna o token a partir do caracter(tirando o bearer e espaço).
	}

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token); //Pego id do token.
		Usuario usuario = usuarioRepository.findById(idUsuario).get(); //Filtra no BD o usuário pelo ID.
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (usuario, null, usuario.getAuthorities()); //Recupera credenciais de autenticação do usuário(que já fez a autenticação). cria usuário de autenticação, passando usuário de id pego pelo banco, senha nula pois já está autenticado e perfil.
		SecurityContextHolder.getContext().setAuthentication(authentication); //Considera que está autenticado(força a autenticação).
	}
	
	
	
}
