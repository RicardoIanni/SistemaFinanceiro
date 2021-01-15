package br.com.ricardoianni.inovacaoapp.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.servico.Material;
import br.com.ricardoianni.inovacaoapp.domain.servico.MaterialRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServico;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServicoCliente;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServicoClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServicoRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;
import br.com.ricardoianni.inovacaoapp.domain.servico.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private PacoteServicoRepository pacoteServicoRepository;
	
	@Autowired
	private PacoteServicoClienteRepository pacoteServicoClienteRepository;
	
	public void saveServico(Servico servico) {
		
		servicoRepository.save(servico);
	}
	
	public void deleteServico(Servico servico, 
							  List<Material> materiais) {
	
		for (Material material : materiais) {
			materialRepository.deleteById(material.getIdMaterial());
		}
		
		servicoRepository.deleteById(servico.getIdServico());
	}
	
	public void saveMaterial(Material material) {
		
		materialRepository.save(material);
	}
	
	public void deleteMaterial(Material material) {
		
		materialRepository.deleteById(material.getIdMaterial());
	}
	
	public void savePacoteServico(PacoteServico pctServico) {
	
		pacoteServicoRepository.save(pctServico);
	}
	
	public void savePacoteServicoCliente(PacoteServicoCliente pctCliente) {
	
		pacoteServicoClienteRepository.save(pctCliente);
	}
	
}
