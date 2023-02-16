package br.api.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.api.forum.controller.dto.DetalhesTopicoDto;
import br.api.forum.controller.dto.TopicoDto;
import br.api.forum.controller.form.AtualizacaoTopicoForm;
import br.api.forum.controller.form.TopicoForm;
import br.api.forum.modelo.Topico;
import br.api.forum.repository.CursoRepository;
import br.api.forum.repository.TopicoRepository;

@RequestMapping("/topicos") //Requisição para essa URL.
@RestController //Classe controladora REST.
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	@Cacheable(value="listaDeTopicos") //Cache no método com nome.
	public Page<TopicoDto> listar(@RequestParam(required = false) String cursoNome, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) { //Método retornará um JSON com Page(lista com paginação) de topicos. Anotação Request indica obrigação(true) ou não(sem ()) de parâmetros. Endereço de teste: URL/topicos?page=0&size=3&sort=dataCriacao,asc(Objeto paginacao contém quantidade, número da página e ordenação na URL da requisição).
		if(cursoNome == null) { //Se não houver valor depois de ?(que é o valor do parâmetro).
			Page<Topico> topicos = topicoRepository.findAll(paginacao); //Seleciona todos os registros de Topico do DB com paginação(lista com paginação retorna objeto do tipo Page).
			
			return TopicoDto.converter(topicos); //Retorna uma lista com os objetos.
		} else {
			Page<Topico> topicos = topicoRepository.findByCurso_Nome(cursoNome, paginacao); //Filtra pelo atributo nome da classe Curso.
			
			return TopicoDto.converter(topicos);
		}
	}

	@PostMapping //Requisição do tipo POST.
	@Transactional
	@CacheEvict(value="listaDeTopicos", allEntries = true) //Limpa o cache(todos os registros) de nome passado em () quando efetuado esse método POST.
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder){ //Pega o parâmetro do corpo da requisição. Resposta com código 201(Não retorna a entidade Topico, mas as classes de donínio(<TopicoDto>). Valido os atributos de TopicoForm com anotações(se houver atributo não validado retorna código 400).
		Topico topico = topicoForm.converter(cursoRepository); //topicoForm recebe o conteúdo do JSON(titulo, mensagem e nomeCurso com valores(populando os atributos de topicoForm)) no body via postman.
		topicoRepository.save(topico); //Salvo no DB o topico(com titulo, mensagem e curso(objeto topico)).
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); //Passa o endereço da API com Id, passando Id do topico criado, criando a URL completa do servidor.
		
		return ResponseEntity.created(uri).body(new TopicoDto(topico)); //Cria e retorna URI com corpo, com informações do topico(id, titulo, mesnagem, dataCriacao).
	}
	
	@GetMapping("/{id") //Requisição GET que Recebe o Id dinâmico(pode vir qualquer número). /topicos/2 por exemplo e apresenta o JSON com esse tópico.
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) { //Id no parâmetro virá na URL, /topicos/2 e não ?2. O id que for pego na URL vai passar par objeto id do tipo Long dentro de ().
		Optional<Topico> topico = topicoRepository.findById(id); //Busca no DB o topico pelo id passado no URL. Optional recebe registro que exista ou não exista.
		if(topico.isPresent()) { //Se houver registro no topico.
			return ResponseEntity.ok(new DetalhesTopicoDto(topico.get())) ; //Retorna o JSON com corpo e atributos do DetalhesTopicoDto(id, titulo, mensagem, dataCriacao, nomeAutor, status, respostas) com valores preenchidos com topico. Método get() deve vir após o topico.
		}
		
		return ResponseEntity.notFound().build(); //Retorna erro 404.
	}
	
	@PutMapping("/{id}") //Requisição PUT que atualiza informações(pelo Id).
	@Transactional //Comitará a transação no final do método.
	@CacheEvict(value="listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){ //Serão atualizadas informações dos atributos de AtualizacaoTopicoForm, passados no corpo do JSON.
		Optional<Topico> optional = topicoRepository.findById(id); 
		if(optional.isPresent()) { 
			Topico topico = form.atualizar(id, topicoRepository); //Método atualizar retornará um objeto topico já com o topico de id passado no () e passando os valores dos atributos titulo e mensagem(atributos de AtualizacaoTopicoForm) para os atributos titulo e mensagem da classe Topico. 
			return ResponseEntity.ok(new TopicoDto(topico)); //Retornará corpo(ok) no JSON em resposta, mostrando novo TopicoDto com seus atributos(titulo, mensagem) alterados no método atualizar e atributos id e dataDeCriacao que serão gerados automáticamente.
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
	@DeleteMapping("/{id}") 
	@CacheEvict(value="listaDeTopicos", allEntries = true)
	public ResponseEntity remover(@PathVariable Long id){
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			topicoRepository.deleteById(id); //Deleta o topico pelo id.
			return ResponseEntity.ok().build(); //Retorna 200.
		}
		
		return ResponseEntity.notFound().build();
	}
	
	/* Aplicar corpo do JSON em método POST,PUT.
	 * {
	 * 		"atributo":"valor",
	 * 		"atributo2":"valor"
	 * }
	 */
	
}
