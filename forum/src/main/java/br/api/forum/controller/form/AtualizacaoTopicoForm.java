package br.api.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.api.forum.modelo.Topico;
import br.api.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm { //Classe criada com intuito de atualização de informações do Topico.

	@NotNull @NotEmpty @Length(min = 2) //Mínimo 5 caracteres.
	private String titulo;
	@NotNull @NotEmpty @Length(max = 50) //Máximo 50 caracteres.
    private String mensagem;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getReferenceById(id); //Traz do BD o Topico pelo id.
		
		topico.setTitulo(this.titulo); //topico do BD(desatualizado) recebe as novas informações vindas do JSON(serão passadas no corpo apenas os dois atributos dessa classe(titulo, mensagem)) para atualizar(processo só será executado quando ).
		topico.setMensagem(this.mensagem);
		
		return topico;
	} 
	
}
