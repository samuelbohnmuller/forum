package br.api.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.api.forum.modelo.Topico;

public class TopicoDto { //Classe dto retorna apenas alguns atributos de uma classe(usado em métodos GET).

	private Long id; 
    private String titulo; 
    private String mensagem; 
    private LocalDateTime dataCriacao;
    
    
    public TopicoDto(Topico topico) { //Quando new em TopicoDto, é necessário passar um objeto Topico em ().
        this.id = topico.getId(); //Atributos de TopicoDto recebem os valores contidos nos atributos mencionados nesse construtor, de Topico.
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }
    
    public TopicoDto() {
		super();
	}

	public static Page<TopicoDto> converter(Page<Topico> topicos) {
		return topicos.map(TopicoDto::new); //Retorna Page de TopicoDto(efetuada conversão de Topico para TopicoDto).
	}
    
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	} 
    
}
