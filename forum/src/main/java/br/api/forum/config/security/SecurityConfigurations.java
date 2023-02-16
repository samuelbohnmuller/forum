package br.api.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.api.forum.repository.UsuarioRepository;

@EnableWebSecurity //Habilita a segurança do Spring.
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{ //Extendemos de classe com métodos de segurança.

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	@Bean //Avisa que método é de autenticacaoManager.
	protected AuthenticationManager authenticationManager() throws Exception { //Injeta a dependência que será usada na classe AutenticacaoController para autenticação.
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //Autorização.
	    http.authorizeRequests()
	    	.antMatchers(HttpMethod.GET,"/topicos").permitAll() 
	    	.antMatchers(HttpMethod.GET,"/topicos/*").permitAll() //Permite URLs do tipo GET depois de /topicos/algumacoisa.
	    	.antMatchers(HttpMethod.POST,"/auth").permitAll() //Liberado requisição de login.
	    	.anyRequest().authenticated()
	    	.and().csrf().disable() //Desabilita o csrf(pois por token não é necessário).
	    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Autenticação por Token(stateless).
	    	.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class); //Adicionar um novo filtro depois do primeiro(que deve ser executado primeiro) passando o token no filtro.
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Autenticação.
		auth.userDetailsService(autenticacaoService).
			passwordEncoder(new BCryptPasswordEncoder());//Passo o otributo da Classe que contém a lógica de autenticação e encripto ele.
	}
	
}
