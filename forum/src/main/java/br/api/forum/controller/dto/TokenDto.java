package br.api.forum.controller.dto;

public class TokenDto { //Classe criada para guardar o token e tipo do mesmo(valores para seus atributos serão passados na resposta do retorno do método de autenticação).

	private String token;
	private String tipo;

	public TokenDto(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
