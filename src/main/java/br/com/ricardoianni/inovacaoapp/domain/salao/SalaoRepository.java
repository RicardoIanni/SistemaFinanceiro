package br.com.ricardoianni.inovacaoapp.domain.salao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaoRepository extends JpaRepository<Salao, Integer> {
	
	public Salao findByUser(String user);
	
	public Salao findByIdSalao(Integer idSalao);

}
