package br.api.forum.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm { //Pega o login e senha do cliente.

	private String email; //Atributos que representam o login e senha do usuário que serão colocados no ()(que virão no corpo da requisição) no método de autenticação.
	private String senha;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha); //Retorna uma nova instancia passando sob parâmetro o email e senha que o usuário passar no JSON.
	}
	
}
