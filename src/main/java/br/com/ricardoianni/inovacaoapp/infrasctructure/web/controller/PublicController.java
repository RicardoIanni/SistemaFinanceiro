package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

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
import br.com.ricardoianni.inovacaoapp.domain.email.TipoEmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.endereco.TipoEnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TipoTelefoneRepository;
import br.com.ricardoianni.inovacaoapp.utils.StringUtils;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private SalaoService salaoService;
	
	@Autowired
	private TipoEmailRepository tipoEmailRepository;
	
	@Autowired
	private TipoTelefoneRepository tipoTelefoneRepository;
	
	@Autowired
	private TipoEnderecoRepository tipoEnderecoRepository;
	
	@GetMapping("/salao/new")
	public String newSalao(Model model) {
		
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.setSaveOK(model, false);
		
		model.addAttribute("salao", new Salao());
		model.addAttribute("email", new EnderecoEmail());
		model.addAttribute("telefone", new Telefone());
		model.addAttribute("endereco", new Endereco());
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		return "cadsalao";
	}	
	
	@PostMapping("/salao/save")
	public String saveSalao(@ModelAttribute("salao") @Valid Salao salao,
							Errors erroSalao,
							@ModelAttribute("email") @Valid EnderecoEmail email,
							Errors erroEmail,
							@ModelAttribute("telefone") Telefone telefone,
							@ModelAttribute("endereco") Endereco endereco,
							Model model) {
		
		boolean hasError = false;
		
		if (erroSalao.hasErrors()) {
			hasError = true;
		}
		
		if (erroEmail.hasErrors()) {
			hasError = true;
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
				return "login";
			} catch (ValidationException e) {
				erroSalao.rejectValue("user", null, e.getMessage());
			}
			
		}
		
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.setSaveOK(model, false);
		
		ControllerHelper.addTipoEmailToRequest(tipoEmailRepository, model);
		ControllerHelper.addTipoTelefoneToRequest(tipoTelefoneRepository, model);
		ControllerHelper.addTipoEnderecoToRequest(tipoEnderecoRepository, model);
		
		return "cadsalao";
	}
	
}