package br.com.ricardoianni.inovacaoapp.infrasctructure.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Role role = SecurityUtils.loggedUser().getRole();
		
		if (role == Role.SALAO) {
			response.sendRedirect("admin/home");
		
		} else {
			throw new IllegalStateException("Erro na autenticação");
		
		}
	}
}