package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ricardoianni.inovacaoapp.application.ValidationException;
import br.com.ricardoianni.inovacaoapp.application.service.SalaoService;
import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.domain.email.EmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.email.TipoEmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.endereco.EnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.TipoEnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.salao.SalaoRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TelefoneRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TipoTelefoneRepository;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;
import br.com.ricardoianni.inovacaoapp.utils.StringUtils;

@Controller
@RequestMapping(path = "/admin")
public class SalaoController {
	
	@Autowired
	private SalaoService salaoService;
	
	@Autowired
	private SalaoRepository salaoRepository;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private TipoEmailRepository tipoEmailRepository;
	
	@Autowired
	private TipoTelefoneRepository tipoTelefoneRepository;
	
	@Autowired
	private TipoEnderecoRepository tipoEnderecoRepository;
	
	@GetMapping("/salao/edit")
	public String editSalao(Model model) {
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.setSaveOK(model, false);
		
		Integer idSalao = SecurityUtils.loggedSalao().getIdSalao();
		
		Salao salao = salaoRepository.findByIdSalao(idSalao);
		
		List<EnderecoEmail> emails = emailRepository.findBySalaoEmail(salao);
		EnderecoEmail email = emails.get(0);
		
		List<Telefone> telefones = telefoneRepository.findBySalaoTelefone(salao);
		Telefone telefone = telefones.get(0);
		
		List<Endereco> enderecos = enderecoRepository.findBySalaoEndereco(salao);
		Endereco endereco = enderecos.get(0);
		
		model.addAttribute("salao", salao);
		model.addAttribute("email", email);
		model.addAttribute("telefone", telefone);
		model.addAttribute("endereco", endereco);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		return "editsalao";
	}
	
	@PostMapping("/salao/save")
	public String saveCliente(@ModelAttribute("salao") @Valid Salao salao,
							  Errors erroSalao,
							  @ModelAttribute("email") @Valid EnderecoEmail email,
							  Errors erroEmail,
							  @ModelAttribute("telefone") Telefone telefone,
							  @ModelAttribute("endereco") Endereco endereco,
							  Model model) {
		
		boolean isEdit = true;
		boolean hasError = false;
		boolean saveOk = true;
		
		if (erroSalao.hasErrors()) {
			hasError = true;
			saveOk = false;
		}
		
		if (erroEmail.hasErrors()) {
			hasError = true;
			saveOk = false;
		}
		
		if (!hasError) {
			email.setSalaoEmail(salao);
			telefone.setSalaoTelefone(salao);
			endereco.setSalaoEndereco(salao);

			String numeroCnpj = StringUtils.replaceString(salao.getCnpj(), '.', null);
			numeroCnpj = StringUtils.replaceString(numeroCnpj, '/', null);
			numeroCnpj = StringUtils.replaceString(numeroCnpj, '-', null);
			salao.setCnpj(numeroCnpj);
			
			String numeroTelefone = StringUtils.replaceString(telefone.getNumTelefone(), '-', null);
			telefone.setNumTelefone(numeroTelefone);
			
			String numeroCep = StringUtils.replaceString(endereco.getCep(), '-', null);
			endereco.setCep(numeroCep);
			
			try {
				salaoService.saveSalao(salao, email, telefone, endereco);
			} catch (ValidationException e) {
				erroSalao.rejectValue("user", null, e.getMessage());
			}
			
		}
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		ControllerHelper.setEditMode(model, isEdit);
		ControllerHelper.setSaveOK(model, saveOk);
		
		return "editsalao";
	}
	
}