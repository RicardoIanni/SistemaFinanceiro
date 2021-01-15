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

import br.com.ricardoianni.inovacaoapp.application.service.ProdutoService;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.PessoaFilter;
import br.com.ricardoianni.inovacaoapp.domain.produto.Produto;
import br.com.ricardoianni.inovacaoapp.domain.produto.ProdutoRepository;
import br.com.ricardoianni.inovacaoapp.domain.produto.UnidadeMedidaRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.NavigatorUtils;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/admin/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;
	
	@Autowired
	private ServicoController servicoController;
		
	@GetMapping("/")
	public String produto(@ModelAttribute("filter") PessoaFilter filter,
			  			  Model model) {
		Salao salao = SecurityUtils.loggedSalao();		
		String textFilter;
		List<Produto> produtos;
		
		try {
			textFilter = filter.getTexto();
		} catch (NullPointerException e) {
			textFilter = null;
		} 
		
		if (textFilter != null) {
			produtos = produtoRepository.findBySalaoProdutoAndDescricaoContainingOrderByDescricao(salao, textFilter);
		} else {
			produtos = produtoRepository.findBySalaoProdutoOrderByDescricao(salao);
		}
		
		filter.setTexto(null);
		
		model.addAttribute("filter",   filter  );
		model.addAttribute("produtos", produtos);
		
		return "produto";
	}
	
	@GetMapping("/new")
	public String newProduto(Model model,
							 HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Produto produto = new Produto();
		produto.setSalaoProduto(salao);
		
		model.addAttribute("produto", produto);
		
		ControllerHelper.addUnidadeMedidaToRequest(unidadeMedidaRepository, model);

		ControllerHelper.setEditMode(model, false);
		
		NavigatorUtils.setUrlToBack(request, "cadproduto");
		
		return "cadproduto";
	}
	
	@PostMapping("/save")
	public String saveProduto(@ModelAttribute("produto") @Valid Produto produto,
							  Errors erroProduto,
							  @RequestParam("edit") Boolean isEdit,
							  Model model) {
		
		if (!erroProduto.hasErrors()) {
			Salao salao = SecurityUtils.loggedSalao();
			String url = NavigatorUtils.getUrlToBack();
			
			produto.setSalaoProduto(salao);
			
			produtoService.saveProduto(produto);
			
			if (url.contains("home")) {
				return produto(new PessoaFilter(), model);
			} else if (url.contains("servico")) {
				return servicoController.servico(new PessoaFilter(), model);
			} else {
				return url;
			}
		}
		
		ControllerHelper.setEditMode(model, isEdit);
		
		ControllerHelper.addUnidadeMedidaToRequest(unidadeMedidaRepository, model);
		
		return "cadproduto";
	}
	
	@PostMapping("/edit")
	public String editProduto(@RequestParam("idProdEdit") Integer idProduto,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Produto produto = produtoRepository.findBySalaoProdutoAndIdProduto(salao, idProduto);
		
		model.addAttribute("produto", produto);

		ControllerHelper.setEditMode(model, true);
		
		ControllerHelper.addUnidadeMedidaToRequest(unidadeMedidaRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadproduto");
		
		return "cadproduto";
	}
	
	@PostMapping(path = "/delete")
	public String deleteProduto(@RequestParam("idProdDelele") Integer idProduto,
								Model model,
								HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "cadproduto");
		String url = NavigatorUtils.getUrlToBack();
		
		Produto produto = produtoRepository.findBySalaoProdutoAndIdProduto(salao, idProduto);
		
		produtoService.deleteProduto(produto);
		
		return url;
	}
	
}
