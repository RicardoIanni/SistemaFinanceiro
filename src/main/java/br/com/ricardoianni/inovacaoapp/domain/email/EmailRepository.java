package br.com.ricardoianni.inovacaoapp.domain.email;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface EmailRepository extends JpaRepository<EnderecoEmail, Integer> {

	public List<EnderecoEmail> findBySalaoEmail(Salao salaoEmail);
	
	public List<EnderecoEmail> findBySalaoEmailAndClienteEmail(Salao salaoEmail, Cliente clienteEmail);
	
	public List<EnderecoEmail> findBySalaoEmailAndFornecedorEmail(Salao salaoEmail, Fornecedor fornecedorEmail);
	
	public List<EnderecoEmail> findBySalaoEmailAndFuncionarioEmail(Salao salaoEmail, Funcionario funcionarioEmail);
	
}
