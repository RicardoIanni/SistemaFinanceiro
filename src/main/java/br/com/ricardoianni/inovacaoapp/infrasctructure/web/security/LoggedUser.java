package br.com.ricardoianni.inovacaoapp.infrasctructure.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.usuario.Usuario;
import br.com.ricardoianni.inovacaoapp.utils.CollectionUtils;

@SuppressWarnings("serial")
public class LoggedUser implements UserDetails {
	
	private Usuario usuario;
	private Role role;
	private Collection<? extends GrantedAuthority> roles;
	
	public LoggedUser(Usuario usuario) {
		this.usuario = usuario;
		
		Role role;
		
		if (usuario instanceof Salao) {
			role = Role.SALAO;
		
		} else {
			throw new IllegalStateException("Usuário inválido");
		}
		
		this.role = role;
		this.roles = CollectionUtils.listOf(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getUser();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Role getRole() {
		return role;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}