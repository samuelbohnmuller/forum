package br.api.forum.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.api.forum.modelo.Usuario;
import br.api.forum.repository.UsuarioRepository;

@Service 
public class AutenticacaoService implements UserDetailsService{ //Serviço(Classe) que tem a lógica de autenticação.

	@Autowired
	private UsuarioRepository repository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //Quando efetuar o login, será chamado esse método. Variável username representa o email e senha preenchidos.
		Optional<Usuario> usuario = repository.findByEmail(username); //Busca na tabela Usuario no DB o email digitado pelo usuário no formulário de login.
		if(usuario.isPresent()) {
			return usuario.get(); //Pega o objeto usuario.
		}
		
		throw new UsernameNotFoundException("Dados inválidos"); //Retorna mensagem de erro ao usuário caso o login esteja inválido.
    }
	
}
