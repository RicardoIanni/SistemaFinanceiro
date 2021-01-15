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

import br.com.ricardoianni.inovacaoapp.application.service.FornecedorService;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FornecedorRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.PessoaFilter;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.email.EmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.domain.email.TipoEmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.endereco.EnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.TipoEnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TelefoneRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TipoTelefoneRepository;
import br.com.ricardoianni.inovacaoapp.utils.NavigatorUtils;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;
import br.com.ricardoianni.inovacaoapp.utils.StringUtils;

@Controller
@RequestMapping(path = "/admin/fornecedor")
public class FornecedorController {
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private TipoEmailRepository tipoEmailRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private TipoTelefoneRepository tipoTelefoneRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private TipoEnderecoRepository tipoEnderecoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@GetMapping("/")
	public String fornecedor(@ModelAttribute("filter") PessoaFilter filter,
			  				 Model model) {
		Salao salao = SecurityUtils.loggedSalao();		
		String textFilter;
		List<Fornecedor> fornecedores;
		
		try {
			textFilter = filter.getTexto();
		} catch (NullPointerException e) {
			textFilter = null;
		} 
		
		if (textFilter != null) {
			fornecedores = fornecedorRepository.findBySalaoFornecedorAndNomeContainingOrderByNome(salao, textFilter);
		} else {
			fornecedores = fornecedorRepository.findBySalaoFornecedorOrderByNome(salao);
		}
		
		filter.setTexto(null);
		
		model.addAttribute("filter",       filter  );
		model.addAttribute("fornecedores", fornecedores);
		
		return "fornecedor";
	}
	
	@GetMapping("/new")
	public String newFornecedor(Model model,
				 				HttpServletRequest request) {
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setPf_pj("pj");
		
		model.addAttribute("fornecedor", fornecedor);
		model.addAttribute("email", new EnderecoEmail());
		model.addAttribute("telefone", new Telefone());
		model.addAttribute("endereco", new Endereco());
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		ControllerHelper.setEditMode(model, false);
		
		NavigatorUtils.setUrlToBack(request, "cadfornecedor");
		
		return "cadfornecedor";
	}
	
	@PostMapping("/save")
	public String saveFornecedor(@ModelAttribute("fornecedor") @Valid Fornecedor fornecedor,
								 Errors erroFornecedor,
							     @ModelAttribute("email") @Valid EnderecoEmail email,
							     Errors erroEmail,
							     @ModelAttribute("telefone") Telefone telefone,
							     @ModelAttribute("endereco") Endereco endereco,
								 @RequestParam("edit") Boolean isEdit,
							     Model model) {
		
		String url = NavigatorUtils.getUrlToBack();
		boolean hasError = false;
		
		if (erroFornecedor.hasErrors()) {
			hasError = true;
		}
		
		if (erroEmail.hasErrors()) {
			hasError = true;
		}
		
		if (!hasError) {
			Salao salao = SecurityUtils.loggedSalao();
			
			fornecedor.setSalaoFornecedor(salao);
			email.setSalaoEmail(salao);
			email.setFornecedorEmail(fornecedor);
			telefone.setSalaoTelefone(salao);
			telefone.setFornecedorTelefone(fornecedor);
			endereco.setSalaoEndereco(salao);
			endereco.setFornecedorEndereco(fornecedor);
			
			if (! (fornecedor.getCnpj().isBlank() || fornecedor.getCnpj().isEmpty() ) ) {
				String numeroCnpj = StringUtils.replaceString(fornecedor.getCnpj(), '.', null);
				numeroCnpj = StringUtils.replaceString(numeroCnpj, '/', null);
				numeroCnpj = StringUtils.replaceString(numeroCnpj, '-', null);
				fornecedor.setCnpj(numeroCnpj);
			}
			
			if (! (fornecedor.getCpf().isBlank() || fornecedor.getCpf().isEmpty() ) ) {
				String numeroCpf = StringUtils.replaceString(fornecedor.getCpf(), '.', null);
				numeroCpf = StringUtils.replaceString(numeroCpf, '-', null);
				fornecedor.setCpf(numeroCpf);	
			}
			
			String numeroTelefone = StringUtils.replaceString(telefone.getNumTelefone(), '-', null);
			telefone.setNumTelefone(numeroTelefone);
			
			String numeroCep = StringUtils.replaceString(endereco.getCep(), '-', null);
			endereco.setCep(numeroCep);
			
			fornecedorService.saveFornecedor(fornecedor, email, telefone, endereco);
			
			if (url.contains("home")) {
				return fornecedor(null, model);
			} else {
				return url;
			}
		}
		
		ControllerHelper.setEditMode(model, isEdit);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		return "cadfornecedor";
	}
	
	@PostMapping(path = "/edit")
	public String editFornecedor(@RequestParam("idFornEdit") Integer idFornecedor,
								 Model model,
								 HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Fornecedor fornecedor = fornecedorRepository.findBySalaoFornecedorAndIdFornecedor(salao, idFornecedor);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmailAndFornecedorEmail(salao, fornecedor);
		EnderecoEmail email = emails.get(0);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefoneAndFornecedorTelefone(salao, fornecedor);
		Telefone telefone = telefones.get(0);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEnderecoAndFornecedorEndereco(salao, fornecedor);
		Endereco endereco = enderecos.get(0);
		
		model.addAttribute("fornecedor", fornecedor);
		model.addAttribute("email", email);
		model.addAttribute("telefone", telefone);
		model.addAttribute("endereco", endereco);

		ControllerHelper.setEditMode(model, true);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadfornecedor");
		
		return "cadfornecedor";
		
	}
	
	@PostMapping(path = "/delete")
	public String deleteFornecedor(@RequestParam("idFornDelele") Integer idFornecedor,
								   Model model,
								   HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		NavigatorUtils.setUrlToBack(request, "cadfornecedor");
		String url = NavigatorUtils.getUrlToBack();
		
		Fornecedor fornecedor = fornecedorRepository.findBySalaoFornecedorAndIdFornecedor(salao, idFornecedor);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmailAndFornecedorEmail(salao, fornecedor);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefoneAndFornecedorTelefone(salao, fornecedor);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEnderecoAndFornecedorEndereco(salao, fornecedor);
	
		fornecedorService.deleteFornecedor(fornecedor, emails, telefones, enderecos);
		
		return url;
	}
}
