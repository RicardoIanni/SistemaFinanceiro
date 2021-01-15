package br.com.ricardoianni.inovacaoapp.domain.telefone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {

	public List<Telefone> findBySalaoTelefone(Salao salaoTelefone);
	
	public List<Telefone> findBySalaoTelefoneAndClienteTelefone(Salao salaoTelefone, Cliente clienteTelefone);
	
	public List<Telefone> findBySalaoTelefoneAndFornecedorTelefone(Salao salaoTelefone, Fornecedor fornecedorTelefone);
	
	public List<Telefone> findBySalaoTelefoneAndFuncionarioTelefone(Salao salaoTelefone, Funcionario funcionarioTelefone);
	
}
