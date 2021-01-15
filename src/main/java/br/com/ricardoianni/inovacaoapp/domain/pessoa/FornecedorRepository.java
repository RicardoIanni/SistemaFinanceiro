package br.com.ricardoianni.inovacaoapp.domain.pessoa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
	
	public List<Fornecedor> findBySalaoFornecedorOrderByNome(Salao salaoFornecedor);
	
	public List<Fornecedor> findBySalaoFornecedorAndNomeContainingOrderByNome(Salao salaoFornecedor, String text);
	
	public Fornecedor findBySalaoFornecedorAndIdFornecedor(Salao salaoFornecedor, Integer idFornecedor);

}
