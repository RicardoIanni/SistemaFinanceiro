package br.com.ricardoianni.inovacaoapp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.application.ValidationException;
import br.com.ricardoianni.inovacaoapp.domain.email.EmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.endereco.EnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.salao.SalaoRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TelefoneRepository;

@Service
public class SalaoService {

	@Autowired
	private SalaoRepository salaoRepository;
	
@	Autowired
	private EmailRepository emailRepository;
		
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	
	public void saveSalao(Salao salao, 
						  EnderecoEmail email,
						  Telefone telefone,
						  Endereco endereco) throws ValidationException {
		
		System.out.println(validateUser(salao.getUser(), salao.getIdSalao()));
		
		if (!validateUser(salao.getUser(), salao.getIdSalao())) {
			throw new ValidationException("Nome de usuário indisponível.");
		}
		
		if (salao.getIdSalao() != null) {
			Salao salaoDB = salaoRepository.findByIdSalao(salao.getIdSalao());
			salao.setUser(salaoDB.getUser());
			salao.setPassword(salaoDB.getPassword());
		} else {
			salao.encryptPassword();
		}
		
		salaoRepository.save(salao);
		emailRepository.save(email);
		telefoneRepository.save(telefone);
		enderecoRepository.save(endereco);
		
	}
	
	private boolean validateUser(String user, Integer id) {
		Salao salao = salaoRepository.findByUser(user);
		
		if (salao != null) {
			if (id == null) {
				return false;
			}
			
			if (! salao.getIdSalao().equals(id)) {
				return false;
			}
		}
		
		return true;
	}
}