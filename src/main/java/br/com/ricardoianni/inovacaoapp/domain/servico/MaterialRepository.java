package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
	
	public List<Material> findBySalaoMaterialAndServicoMaterialOrderByIdMaterial(Salao salaoMaterial, Servico servicoMaterial);
	
	public Material findBySalaoMaterialAndServicoMaterialAndIdMaterial(Salao salaoMaterial, Servico servicoMaterial, Integer idMaterial);
	
}