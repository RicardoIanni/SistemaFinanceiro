package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ricardoianni.inovacaoapp.application.service.ServicoService;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.PessoaFilter;
import br.com.ricardoianni.inovacaoapp.domain.produto.ProdutoRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.servico.Material;
import br.com.ricardoianni.inovacaoapp.domain.servico.MaterialRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServico;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServicoCliente;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServicoRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;
import br.com.ricardoianni.inovacaoapp.domain.servico.ServicoRepository;
import br.com.ricardoianni.inovacaoapp.utils.NavigatorUtils;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/admin/servico")
public class ServicoController {
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private PacoteServicoRepository pacoteServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/")
	public String servico(@ModelAttribute("filter") PessoaFilter filter,
			  			  Model model) {
		Salao salao = SecurityUtils.loggedSalao();		
		String textFilter;
		List<Servico> servicos;
		
		try {
			textFilter = filter.getTexto();
		} catch (NullPointerException e) {
			textFilter = null;
		} 
		
		if (textFilter != null) {
			servicos = servicoRepository.findBySalaoServicoAndDescricaoContainingOrderByDescricao(salao, textFilter);
		} else {
			servicos = servicoRepository.findBySalaoServicoOrderByDescricao(salao);
		}
		
		filter.setTexto(null);
		
		model.addAttribute("filter",   filter  );
		model.addAttribute("servicos", servicos);
		
		return "servico";
	}
	
	@GetMapping("/new")
	public String newServico(Model model,
			  				 HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "cadservico");
		
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.setAddMode(model, false);
		
		model.addAttribute("servico", new Servico());
		
		ControllerHelper.addProdutoToRequest(produtoRepository, model, salao);
		
		return "cadservico";
	}
	
	@PostMapping("/save")
	public String saveServico(@ModelAttribute("servico") @Valid Servico servico,
							  Errors erroServico,
							  @RequestParam("editMode") boolean isEdit,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		boolean isAdd = false;
		
		if (!erroServico.hasErrors()) {
			servico.setSalaoServico(salao);
					
			servicoService.saveServico(servico);
			
			Material material = new Material();
			material.setServicoMaterial(servico);

			ControllerHelper.setEditMode(model, true);
			
			if (!isEdit) {
				isAdd = true;
			} 	
			
			ControllerHelper.setAddMode(model, isAdd);
			
			model.addAttribute("material", material);
			
			boolean hasProduto = produtoRepository.count() > 0;
			model.addAttribute("hasProduto", hasProduto);
			
			ControllerHelper.addMaterialToRequest(materialRepository, model, salao, servico);	
		} else {
			ControllerHelper.setEditMode(model, isEdit);
			ControllerHelper.setAddMode(model, false);
		}
		
		ControllerHelper.addProdutoToRequest(produtoRepository, model, salao);

		return "cadservico";
	}
	
	@GetMapping("/edit")
	public String editServico(@RequestParam("idServEdit") Integer idServico,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "cadservico");
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.setAddMode(model, false);
		
		Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
		
		model.addAttribute("servico", servico);
		
		boolean hasProduto = produtoRepository.count() > 0;
		model.addAttribute("hasProduto", hasProduto);
		
		ControllerHelper.addMaterialToRequest(materialRepository, model, salao, servico);
		
		return "cadservico";
	}
	
	@PostMapping("/delete")
	public String deleteServico(@RequestParam("idServDelete") Integer idServico,
								Model model,
								HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "cadservico");
		String url = NavigatorUtils.getUrlToBack();

		Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
		List<Material> materiais = materialRepository.findBySalaoMaterialAndServicoMaterialOrderByIdMaterial(salao, servico);
		
		servicoService.deleteServico(servico, materiais);
		
		return url;
	}
	
	@GetMapping("/material/new")
	public String newMaterial(@RequestParam("idServico") Integer idServico,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "cadservico");
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.setAddMode(model, true);
		
		Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
		
		model.addAttribute("servico", servico);
		
		Material material = new Material();
		material.setServicoMaterial(servico);

		model.addAttribute("material", material);
		
		ControllerHelper.addMaterialToRequest(materialRepository, model, salao, servico);
		ControllerHelper.addProdutoToRequest(produtoRepository, model, salao);
		
		return "cadservico";
	}
	
	@PostMapping("/material/save")
	public String saveServico(@ModelAttribute("material") Material material,
							  @RequestParam("editMode") boolean isEdit,
							  @RequestParam("addMode") boolean isAdd,
							  Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, material.getServicoMaterial().getIdServico());
		
		material.setSalaoMaterial(salao);
		
		servicoService.saveMaterial(material);
		
		model.addAttribute("servico", servico);
		
		ControllerHelper.setEditMode(model, isEdit);
		ControllerHelper.setAddMode(model, false);
		
		ControllerHelper.addProdutoToRequest(produtoRepository, model, salao);
		ControllerHelper.addMaterialToRequest(materialRepository, model, salao, servico);
		
		return "cadservico"; 
	}
	
	@GetMapping("/material/edit")
	public String editMaterial(@RequestParam("idServEdit") Integer idServico,
							   @RequestParam("idMatEdit") Integer idMaterial,
						  	   Model model,
						  	   HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		boolean isAdd = true;

		NavigatorUtils.setUrlToBack(request, "cadservico");
		
		Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
		Material material = materialRepository.findBySalaoMaterialAndServicoMaterialAndIdMaterial(salao, servico, idMaterial);
		
		if (material == null) {
			isAdd = false;
		}
		
		model.addAttribute("servico", servico);
		model.addAttribute("material", material);
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.setAddMode(model, isAdd);
		
		ControllerHelper.addMaterialToRequest(materialRepository, model, salao, servico);
		ControllerHelper.addProdutoToRequest(produtoRepository, model, salao);
		
		return "cadservico";
	}
	
	@PostMapping("/material/delete")
	public String deleteMaterial(@RequestParam("idServDelele") Integer idServico,
			   					 @RequestParam("idMatDelele") Integer idMaterial,
			   					 Model model,
			   					 HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		NavigatorUtils.setUrlToBack(request, "cadservico");
		String url = NavigatorUtils.getUrlToBack();

		Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
		
		Material material = materialRepository.findBySalaoMaterialAndServicoMaterialAndIdMaterial(salao, servico, idMaterial);
				
		servicoService.deleteMaterial(material);
		
		return url;
	}

	@GetMapping("/pacote/new")
	public String newPacoteServico(Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		
		ControllerHelper.setEditMode(model, false);
		
		model.addAttribute("pctServico", new PacoteServico());
		
		ControllerHelper.addServicoToRequest(servicoRepository, model, salao);
		
		return "cadpctserv";
	}
	
	@PostMapping("/pacote/save")
	public String savePacoteServico(@ModelAttribute("pctServico") PacoteServico pctServico,
							  		Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		
		pctServico.setSalaoPacoteServico(salao);
				
		servicoService.savePacoteServico(pctServico);
		
		ControllerHelper.setEditMode(model, false);
		
		ControllerHelper.addServicoToRequest(servicoRepository, model, salao);
		
		return "cadpctserv"; 
	}

	@GetMapping("/cliente/new")
	public String newPacoteServicoCliente(Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		
		ControllerHelper.setEditMode(model, false);
		
		model.addAttribute("pctCliente", new PacoteServicoCliente());
		
		ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		ControllerHelper.addPacoteServicoToRequest(pacoteServicoRepository, model, salao);
		
		return "incpctcli";
	}
	
	@PostMapping("/cliente/save")
	public String savePacoteServicoCliente(@ModelAttribute("pctCliente") PacoteServicoCliente pctCliente,
							  		  	   Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		
		pctCliente.setSalaoPctCliente(salao);
		pctCliente.setSaldo(pctCliente.getPacoteServicoPctCliente().getQuantidade());
				
		servicoService.savePacoteServicoCliente(pctCliente);
		
		ControllerHelper.setEditMode(model, false);
		
		ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		ControllerHelper.addPacoteServicoToRequest(pacoteServicoRepository, model, salao);
		
		return "incpctcli"; 
	}
	
}