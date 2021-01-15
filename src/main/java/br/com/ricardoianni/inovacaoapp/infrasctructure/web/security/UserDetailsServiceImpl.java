package br.com.ricardoianni.inovacaoapp.infrasctructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.salao.SalaoRepository;
import br.com.ricardoianni.inovacaoapp.domain.usuario.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SalaoRepository salaoRespository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = salaoRespository.findByUser(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new LoggedUser(usuario);
	}

}