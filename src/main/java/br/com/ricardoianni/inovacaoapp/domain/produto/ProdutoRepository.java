package br.com.ricardoianni.inovacaoapp.domain.produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	public List<Produto> findBySalaoProdutoOrderByDescricao(Salao salaoProduto);

	public List<Produto> findBySalaoProdutoAndDescricaoContainingOrderByDescricao(Salao salaoProduto, String text);

	public Produto findBySalaoProdutoAndIdProduto(Salao salaoProduto, Integer idProduto);

	
}
