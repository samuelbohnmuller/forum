package br.api.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.api.forum.modelo.Curso;
import br.api.forum.modelo.Topico;
import br.api.forum.repository.CursoRepository;

public class TopicoForm { //Nomenclatura com final Form, são usados em métodos POST.

	@NotNull @NotEmpty //Validações não nulo e não vazio no corpo da requisição.
	private String titulo;
	@NotNull @NotEmpty
    private String mensagem;
	@NotNull @NotEmpty
    private String nomeCurso;
    
	public TopicoForm() {
	}
	
	public Topico converter(CursoRepository cursoRepository) { //Injeto o objeto de conexão com o DB de Curso sobre ().
		Curso curso = cursoRepository.findByNome(nomeCurso); //Retornará o valor do atributo nome da classe Curso.
		
		return new Topico(titulo, mensagem, curso); //Retorno uma nova instancia de Topico com 3 atributos(que serão populados via postman no body quando efetuado método POST).
	}
	
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
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
}
