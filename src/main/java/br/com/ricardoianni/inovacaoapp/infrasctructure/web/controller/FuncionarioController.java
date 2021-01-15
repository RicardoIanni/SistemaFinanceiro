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

import br.com.ricardoianni.inovacaoapp.application.service.FuncionarioService;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FuncionarioRepository;
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
@RequestMapping(path = "/admin/funcionario")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
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

	@GetMapping("/")
	public String funcionario(@ModelAttribute("filter") PessoaFilter filter,
			  				  Model model) {
		Salao salao = SecurityUtils.loggedSalao();		
		String textFilter;
		List<Funcionario> funcionarios;
		
		try {
			textFilter = filter.getTexto();
		} catch (NullPointerException e) {
			textFilter = null;
		} 
		
		if (textFilter != null) {
			funcionarios = funcionarioRepository.findBySalaoFuncionarioAndNomeContainingOrderByNome(salao, textFilter);
		} else {
			funcionarios = funcionarioRepository.findBySalaoFuncionarioOrderByNome(salao);
		}
		
		filter.setTexto(null);
		
		model.addAttribute("filter",       filter  );
		model.addAttribute("funcionarios", funcionarios);
		
		return "funcionario";
	}
	
	@GetMapping("/new")
	public String newFuncionario(Model model,
								 HttpServletRequest request) {
		
		model.addAttribute("funcionario", new Funcionario());
		model.addAttribute("email", new EnderecoEmail());
		model.addAttribute("telefone", new Telefone());
		model.addAttribute("endereco", new Endereco());
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadfuncionario");
		
		return "cadfuncionario";
	}
	
	@PostMapping("/save")
	public String saveFuncionario(@ModelAttribute("funcionario") @Valid Funcionario funcionario,
								  Errors erroFuncionario,
							      @ModelAttribute("email") @Valid EnderecoEmail email,
							      Errors erroEmail,
							      @ModelAttribute("telefone") Telefone telefone,
							      @ModelAttribute("endereco") Endereco endereco,
							      Model model) {
		
		String url = NavigatorUtils.getUrlToBack();
		boolean hasError = false;
		
		if (erroFuncionario.hasErrors()) {
			hasError = true;
		}
		
		if (erroEmail.hasErrors()) {
			hasError = true;
		}
		
		if (!hasError) {
			Salao salao = SecurityUtils.loggedSalao();
			
			funcionario.setSalaoFuncionario(salao);
			email.setSalaoEmail(salao);
			email.setFuncionarioEmail(funcionario);
			telefone.setSalaoTelefone(salao);
			telefone.setFuncionarioTelefone(funcionario);
			endereco.setSalaoEndereco(salao);
			endereco.setFuncionarioEndereco(funcionario);
			
			if (! (funcionario.getCpf().isBlank() || funcionario.getCpf().isEmpty() ) ) {
				String numeroCpf = StringUtils.replaceString(funcionario.getCpf(), '.', null);
				numeroCpf = StringUtils.replaceString(numeroCpf, '-', null);
				funcionario.setCpf(numeroCpf);	
			}
			
			String numeroTelefone = StringUtils.replaceString(telefone.getNumTelefone(), '-', null);
			telefone.setNumTelefone(numeroTelefone);
			
			String numeroCep = StringUtils.replaceString(endereco.getCep(), '-', null);
			endereco.setCep(numeroCep);
			
			funcionarioService.saveFuncionario(funcionario, email, telefone, endereco);

			if (url.contains("home")) {
				return funcionario(null, model);
			} else {
				return url;
			}
		}
				
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
				
		return "cadfuncionario";
	}
	
	@GetMapping(path = "/edit")
	public String editCliente(@RequestParam("idFuncEdit") Integer idFuncionario,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Funcionario funcionario = funcionarioRepository.findBySalaoFuncionarioAndIdFuncionario(salao, idFuncionario);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmailAndFuncionarioEmail(salao, funcionario);
		EnderecoEmail email = emails.get(0);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefoneAndFuncionarioTelefone(salao, funcionario);
		Telefone telefone = telefones.get(0);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEnderecoAndFuncionarioEndereco(salao, funcionario);
		Endereco endereco = enderecos.get(0);
		
		model.addAttribute("funcionario", funcionario);
		model.addAttribute("email", email);
		model.addAttribute("telefone", telefone);
		model.addAttribute("endereco", endereco);

		ControllerHelper.setEditMode(model, true);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadfuncionario");
		
		return "cadfuncionario";
		
	}
	
	@PostMapping(path = "/delete")
	public String deleteFuncionario(@RequestParam("idFuncDelele") Integer idFuncionario,
								   Model model,
								   HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		NavigatorUtils.setUrlToBack(request, "cadfuncionario");
		String url = NavigatorUtils.getUrlToBack();
		
		Funcionario funcionario = funcionarioRepository.findBySalaoFuncionarioAndIdFuncionario(salao, idFuncionario);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmailAndFuncionarioEmail(salao, funcionario);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefoneAndFuncionarioTelefone(salao, funcionario);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEnderecoAndFuncionarioEndereco(salao, funcionario);
	
		funcionarioService.deleteFuncionario(funcionario, emails, telefones, enderecos);
		
		return url;
	}
	
}