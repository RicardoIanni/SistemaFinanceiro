package br.com.ricardoianni.inovacaoapp.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FuncionarioRepository;
import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.domain.email.EmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.endereco.EnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TelefoneRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmailRepository emailRepository;
		
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	
	public void saveFuncionario(Funcionario funcionario, 
								EnderecoEmail email,
								Telefone telefone,
								Endereco endereco) {
		
		funcionarioRepository.save(funcionario);
		emailRepository.save(email);
		telefoneRepository.save(telefone);
		enderecoRepository.save(endereco);
		
	}
	
	public void deleteFuncionario(Funcionario funcionario,
								  List<EnderecoEmail> emails,
								  List<Telefone> telefones,
								  List<Endereco> enderecos) {
		
		for (Endereco endereco : enderecos) {
			enderecoRepository.deleteById(endereco.getIdEndereco());
		}
		
		for (Telefone telefone : telefones) {
			telefoneRepository.deleteById(telefone.getIdTelefone());
		}
		
		for (EnderecoEmail email : emails) {
			emailRepository.deleteById(email.getIdEmail());
		}
		
		funcionarioRepository.deleteById(funcionario.getIdFuncionario());
		
	}
}
