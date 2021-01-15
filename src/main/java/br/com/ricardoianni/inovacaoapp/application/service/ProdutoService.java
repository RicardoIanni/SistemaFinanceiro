package br.com.ricardoianni.inovacaoapp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.produto.Produto;
import br.com.ricardoianni.inovacaoapp.domain.produto.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
		
	public void saveProduto(Produto produto) {
		
		produtoRepository.save(produto);
		
	}
	
	public void deleteProduto(Produto produto) {
		
		produtoRepository.deleteById(produto.getIdProduto());
		
	}
}
