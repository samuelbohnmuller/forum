package br.api.forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.api.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	Page<Topico> findByCurso_Nome(String cursoNome, Pageable paginacao); //Filtra o atributo nome da classe Curso(Que se relaciona com a classe Topico). O _ faz ligaçã da classe com o atributo da classe. Ordenada com paginação(retorna objeto do tipo Page).

}
