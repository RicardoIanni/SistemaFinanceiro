package br.com.ricardoianni.inovacaoapp.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.infrasctructure.web.security.LoggedUser;

public class SecurityUtils {

	public static LoggedUser loggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		
		return (LoggedUser) authentication.getPrincipal();
	}
	
	public static Salao loggedSalao() {
		LoggedUser loggedUser = loggedUser();
		
		if (loggedUser == null) {
			throw new IllegalStateException("Não existe um usuário logado");
		}
		
		if (!(loggedUser.getUsuario() instanceof Salao)) {
			throw new IllegalStateException("O usuário logado não é válido");
		}
		
		return (Salao) loggedUser.getUsuario();
	}

}