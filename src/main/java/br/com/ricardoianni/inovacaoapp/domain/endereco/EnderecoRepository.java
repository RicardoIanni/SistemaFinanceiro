package br.com.ricardoianni.inovacaoapp.domain.endereco;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	public List<Endereco> findBySalaoEndereco(Salao salaoEndereco);
	
	public List<Endereco> findBySalaoEnderecoAndClienteEndereco(Salao salaoEndereco, Cliente clienteEndereco);
	
	public List<Endereco> findBySalaoEnderecoAndFornecedorEndereco(Salao salaoEndereco, Fornecedor fornecedorEndereco);
	
	public List<Endereco> findBySalaoEnderecoAndFuncionarioEndereco(Salao salaoEndereco, Funcionario funcionarioEndereco);
	
}
