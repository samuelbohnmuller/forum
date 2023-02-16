package br.api.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.api.forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{ //Acesso aos métodos de conexão com o DB.

	Curso findByNome(String nomeCurso); //Busca pelo atributo nome da classe Curso.

}
