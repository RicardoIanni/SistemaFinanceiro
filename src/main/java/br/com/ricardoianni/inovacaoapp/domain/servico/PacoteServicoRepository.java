package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface PacoteServicoRepository extends JpaRepository<PacoteServico, Integer> {

	public List<PacoteServico> findFirstBySalaoPacoteServicoOrderByIdPacoteDesc(Salao salao);

}
