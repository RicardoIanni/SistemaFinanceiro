package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
	
	public List<Conta> findBySalaoContaOrderByDescricao(Salao salaoConta);
	
	public List<Conta> findBySalaoContaAndPadrao(Salao salaoConta, Boolean padrao);
	
	public List<Conta> findBySalaoContaAndDescricaoContainingOrderByDescricao(Salao salaoConta, String text);
	
	public Conta findBySalaoContaAndIdConta(Salao salaoConta, Integer idConta);
	
}