package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

	public List<Servico> findBySalaoServicoOrderByDescricao(Salao salaoServico);
	
	public List<Servico> findBySalaoServicoAndDescricaoContainingOrderByDescricao(Salao salaoServico, String text);
	
	public Servico findBySalaoServicoAndIdServico(Salao salaoServico, Integer idServico);
	
}
