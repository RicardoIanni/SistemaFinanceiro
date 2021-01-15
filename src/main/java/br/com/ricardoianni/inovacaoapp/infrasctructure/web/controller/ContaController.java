package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ricardoianni.inovacaoapp.application.service.ContaService;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Conta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.ContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Pagamento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.PagamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Recebimento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.RecebimentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.TipoContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.PessoaFilter;
import br.com.ricardoianni.inovacaoapp.utils.NavigatorUtils;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/admin/conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TipoContaRepository tipoContaRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private RecebimentoRepository recebimentoRepository;
	
	private Boolean hasMovement = false;
	
	@GetMapping(path = "/")
	public String conta(@ModelAttribute("filter") PessoaFilter filter,
			  			Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		String textFilter;
		List<Conta> contas;
		
		try {
			textFilter = filter.getTexto();
		} catch (NullPointerException e) {
			textFilter = null;
		} 
		
		if (textFilter != null ) {
			contas = contaRepository.findBySalaoContaAndDescricaoContainingOrderByDescricao(salao, textFilter);
		} else {
			contas = contaRepository.findBySalaoContaOrderByDescricao(salao);
		}
		
		ControllerHelper.setHasMovement(model, this.hasMovement );
		model.addAttribute("contas", contas);
		
		this.hasMovement = false;
		
		return "conta";
	}
	
	@GetMapping("/new")
	public String newConta(Model model,
			  				 HttpServletRequest request) {
		
		Conta conta = new Conta();
		
		conta.setData(LocalDate.now());
		
		model.addAttribute("conta", conta);
		
		ControllerHelper.addTipoContaToRequest(tipoContaRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadconta");

		return "cadconta";
	}
	
	@PostMapping("/edit")
	public String editConta(@RequestParam("idContaEdit") Integer idConta,
			  				  Model model,
			  				  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Conta conta = contaRepository.findBySalaoContaAndIdConta(salao, idConta);
		
		model.addAttribute("conta", conta);
		
		ControllerHelper.addTipoContaToRequest(tipoContaRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadconta");

		return "cadconta";
	}
	
	@PostMapping("/save")
	public String saveConta(@ModelAttribute("conta") Conta conta,
							  Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		
		String url = NavigatorUtils.getUrlToBack();
		
		conta.setSalaoConta(salao);
		
		contaService.saveConta(conta);
		
		if (url.contains("home")) {
			return conta(null, model);
		} else {
			return url;
		}
	}
	
	@PostMapping("/delete")
	public String deleteConta(@RequestParam("idContaDelele") Integer idConta,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		boolean hasMovement = false;
		
		NavigatorUtils.setUrlToBack(request, "cadconta");
		String url = NavigatorUtils.getUrlToBack();
		
		Conta conta = contaRepository.findBySalaoContaAndIdConta(salao, idConta);
		
		List<Pagamento> pagamentos = pagamentoRepository.findBySalaoPagamentoAndContaPagamentoOrderByVencimento(salao, conta);
		List<Recebimento> recebimentos = recebimentoRepository.findBySalaoRecebimentoAndContaRecebimentoOrderByVencimento(salao, conta);
		
		if (pagamentos.size() > 0 || recebimentos.size() > 0) {
			hasMovement = true;
		}
		
		this.hasMovement = hasMovement;
		
		if (!hasMovement) {			
			contaService.deleteConta(conta);
		}
		
		if (url.contains("home")) {
			return "cadconta";
		} else {
			return url;
		}
		
	}
	
}