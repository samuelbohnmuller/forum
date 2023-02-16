package br.api.forum.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails{ //Classe representa um usuário no sistema.

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;
	@ManyToMany(fetch = FetchType.EAGER) //Usuário pode ter vários perfils e Perfil pode ter vários Usuários. Quando carregar o Usuario do DB, já carrega a lista de Perfis(por padrão, ManyToMany é lazy, ou seja, não carrega do BD as tabelas com ligações). 
	private List<Perfil> perfis = new ArrayList<>(); //Inicio ela com new para nunca ficar nula.
	private static final long serialVersionUID = 1L;
	
	public Usuario() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis; //Retorna a coleção de Perfis do usuário.
	}

	@Override
	public String getPassword() {
		return this.senha; //Atributo que representa a senha do usuário.
	}

	@Override
	public String getUsername() {
		return this.email; //Atributo que representa o email do usuário.
	}

	@Override
	public boolean isAccountNonExpired() { //Tempo de expiração do login.
		return true; //Não tem.
	}

	@Override
	public boolean isAccountNonLocked() { //Bloqueio de login.
		return true; //Não tem bloqueio.
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; //Não está expirada.
	}

	@Override
	public boolean isEnabled() {
		return true; //Está habilitada.
	}
}
