package br.api.forum.controller.dto;

import java.time.LocalDateTime;

import br.api.forum.modelo.Resposta;

public class RespostaDto {

	private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    
	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId(); //Passo para os atributos de RespostaDto os valores dos mesmos atributos de Resposta.
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome(); //Através do atributo autor(Usuario) busco o atributo nome(Usuario).
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
    
}
