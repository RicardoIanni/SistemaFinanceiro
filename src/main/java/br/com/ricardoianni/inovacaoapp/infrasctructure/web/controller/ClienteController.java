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

import br.com.ricardoianni.inovacaoapp.application.service.ClienteService;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
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
@RequestMapping(path = "/admin/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
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

	@GetMapping(path = "/")
	public String cliente(@ModelAttribute("filter") PessoaFilter filter,
			  			  Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		String textFilter;
		List<Cliente> clientes;
		
		try {
			textFilter = filter.getTexto();
		} catch (NullPointerException e) {
			textFilter = null;
		} 
		
		if (textFilter != null) {
			clientes = clienteRepository.findBySalaoClienteAndNomeContainingOrderByNome(salao, textFilter);
		} else {
			clientes = clienteRepository.findBySalaoClienteOrderByNome(salao);
		}

		filter.setTexto(null);
		
		model.addAttribute("filter",   filter  );
		model.addAttribute("clientes", clientes);
		
		return "cliente";
	}
	
	
	@GetMapping(path = "/new")
	public String newCliente(Model model,
							 HttpServletRequest request) {
		
		model.addAttribute("cliente", new Cliente());
		model.addAttribute("email", new EnderecoEmail());
		model.addAttribute("telefone", new Telefone());
		model.addAttribute("endereco", new Endereco());
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		ControllerHelper.setEditMode(model, false);
		
		NavigatorUtils.setUrlToBack(request, "cadcliente");
		
		return "cadcliente";
	}
	
	@PostMapping(path = "/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
							  Errors erroCliente,
							  @ModelAttribute("email") @Valid EnderecoEmail email,
							  Errors erroEmail,
							  @ModelAttribute("telefone") Telefone telefone,
							  @ModelAttribute("endereco") Endereco endereco,
							  @RequestParam("edit") Boolean isEdit,
							  Model model) {
		
		String url = NavigatorUtils.getUrlToBack();
		boolean hasError = false;
		
		if (erroCliente.hasErrors()) {
			hasError = true;
		}
		
		if (erroEmail.hasErrors()) {
			hasError = true;
		}
		
		if (!hasError) {
			Salao salao = SecurityUtils.loggedSalao();

			cliente.setSalaoCliente(salao);
			email.setSalaoEmail(salao);
			email.setClienteEmail(cliente);
			telefone.setSalaoTelefone(salao);
			telefone.setClienteTelefone(cliente);
			endereco.setSalaoEndereco(salao);
			endereco.setClienteEndereco(cliente);
			
			String numeroCpf = StringUtils.replaceString(cliente.getCpf(), '.', null);
			numeroCpf = StringUtils.replaceString(numeroCpf, '-', null);
			cliente.setCpf(numeroCpf);
			
			String numeroTelefone = StringUtils.replaceString(telefone.getNumTelefone(), '-', null);
			telefone.setNumTelefone(numeroTelefone);
			
			String numeroCep = StringUtils.replaceString(endereco.getCep(), '-', null);
			endereco.setCep(numeroCep);
			
			clienteService.saveCliente(cliente, email, telefone, endereco);
			
			if (url.contains("home")) {
				return cliente(null, model);
			} else {
				return url;
			}			
		}
		
		ControllerHelper.setEditMode(model, isEdit);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		return "cadcliente";
		
	}
	
	@GetMapping(path = "/edit")
	public String editCliente(@RequestParam("idCliEdit") Integer idCliente,
							  Model model,
							  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Cliente cliente = clienteRepository.findBySalaoClienteAndIdCliente(salao, idCliente);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmailAndClienteEmail(salao, cliente);
		EnderecoEmail email = emails.get(0);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefoneAndClienteTelefone(salao, cliente);
		Telefone telefone = telefones.get(0);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEnderecoAndClienteEndereco(salao, cliente);
		Endereco endereco = enderecos.get(0);
		
		model.addAttribute("cliente", cliente);
		model.addAttribute("email", email);
		model.addAttribute("telefone", telefone);
		model.addAttribute("endereco", endereco);

		ControllerHelper.setEditMode(model, true);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		NavigatorUtils.setUrlToBack(request, "cadcliente");
		
		return "cadcliente";
	}
	
	@PostMapping(path = "/delete")
	public String deleteCliente(@RequestParam("idCliDelele") Integer idCliente,
								Model model,
								HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		NavigatorUtils.setUrlToBack(request, "cadcliente");
		String url = NavigatorUtils.getUrlToBack();
		
		Cliente cliente = clienteRepository.findBySalaoClienteAndIdCliente(salao, idCliente);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmailAndClienteEmail(salao, cliente);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefoneAndClienteTelefone(salao, cliente);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEnderecoAndClienteEndereco(salao, cliente);
	
		clienteService.deleteCliente(cliente, emails, telefones, enderecos);
		
		return url;
	}
		
}