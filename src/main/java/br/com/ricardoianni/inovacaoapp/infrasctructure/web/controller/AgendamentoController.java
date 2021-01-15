package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.time.LocalDate;
import java.time.LocalTime;
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

import br.com.ricardoianni.inovacaoapp.application.service.AgendamentoService;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.AgendamentoFilter;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.AgendamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.SituacaoAgendamento;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FuncionarioRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;
import br.com.ricardoianni.inovacaoapp.domain.servico.ServicoRepository;
import br.com.ricardoianni.inovacaoapp.utils.NavigatorUtils;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/admin/agendamento")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping("/")
	public String agendamento(@ModelAttribute("filter") AgendamentoFilter filter, 
							  Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		LocalDate data = LocalDate.now();
		LocalTime hora = LocalTime.now();
		Integer idCliente = 0;
		Integer idFuncionario = 0;
		Integer idServico = 0;
		
		List<Agendamento> agendamentos;
		
		data = filter.getData();
		hora = filter.getHora();
		idCliente = filter.getIdCliente();
		idFuncionario = filter.getIdFuncionario();
		idServico = filter.getIdServico();
			
		if (idCliente == null) {
			idCliente = 0;
		}
			
		if (idFuncionario == null) {
			idFuncionario = 0;
		}
			
		if (idServico == null) {
			idServico = 0;
		}
			
		if (data == null) {
			data = LocalDate.now();
		}
			
		if (hora == null) {
			hora = LocalTime.of(0, 0);
		}
		
		if (idCliente > 0) {
			Cliente cliente = clienteRepository.findBySalaoClienteAndIdCliente(salao, idCliente);
			
			if (idFuncionario > 0) {
				Funcionario funcinario = funcionarioRepository.findBySalaoFuncionarioAndIdFuncionario(salao, idFuncionario);
				
				if (idServico > 0) {
					Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
					
					agendamentos = agendamentoRepository
									.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoAndFuncionarioAgendamentoAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
										(salao, data, hora, cliente, funcinario, servico);
				
				} else {
					
					agendamentos = agendamentoRepository
									.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoAndFuncionarioAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
										(salao, data, hora, cliente, funcinario);
				}
			} else {
				if (idServico > 0) {
					Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
					
					agendamentos = agendamentoRepository
							.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
								(salao, data, hora, cliente, servico);

				} else {

					agendamentos = agendamentoRepository
							.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
								(salao, data, hora, cliente);
							
				}
				
			}
		} else {
			if (idFuncionario > 0) {
				Funcionario funcinario = funcionarioRepository.findBySalaoFuncionarioAndIdFuncionario(salao, idFuncionario);
				
				if (idServico > 0) {
					Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
					
					agendamentos = agendamentoRepository
							.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndFuncionarioAgendamentoAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
								(salao, data, hora, funcinario, servico);
		
				} else {
					
					agendamentos = agendamentoRepository
							.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndFuncionarioAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
								(salao, data, hora, funcinario);

				}
			} else {
				if (idServico > 0) {
					Servico servico = servicoRepository.findBySalaoServicoAndIdServico(salao, idServico);
					
					agendamentos = agendamentoRepository
							.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
								(salao, data, hora, servico);

				} else {
					agendamentos = agendamentoRepository.findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualOrderByDataAgendamentoAscHoraAgendamentoAsc(salao, data, hora);
				}	
			}
		}
		
		model.addAttribute("agendamentos", agendamentos);
		model.addAttribute("filter", filter);
		
		ControllerHelper.addServicoToRequest(servicoRepository, model, salao);
		ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		ControllerHelper.addFuncionarioToRequest(funcionarioRepository, model, salao);
		
		return "agendamento";
	}
	
	@GetMapping("/new")
	public String newAgendamento(Model model,
			 					 HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		Agendamento agendamento = new Agendamento();
		
		agendamento.setDataAgendamento(LocalDate.now());
		
		ControllerHelper.setEditMode(model, false);
		
		model.addAttribute("agendamento", agendamento);
		
		ControllerHelper.addServicoToRequest(servicoRepository, model, salao);
		ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		ControllerHelper.addFuncionarioToRequest(funcionarioRepository, model, salao);
		
		NavigatorUtils.setUrlToBack(request, "incagendamento");
				
		return "incagendamento";
	}
	
	@PostMapping("/edit")
	public String editAgendamento(@RequestParam("idAgendEdit") Integer idAgendamento,
			  					  Model model,
			  					  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		Agendamento agendamento = agendamentoRepository.findBySalaoAgendamentoAndIdAgendamento(salao, idAgendamento);
		
		ControllerHelper.setEditMode(model, true);
		
		model.addAttribute("agendamento", agendamento);
		
		ControllerHelper.addServicoToRequest(servicoRepository, model, salao);
		ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		ControllerHelper.addFuncionarioToRequest(funcionarioRepository, model, salao);
				
		NavigatorUtils.setUrlToBack(request, "incagendamento");
		
		return "incagendamento";
	}
	
	@PostMapping("/save")
	public String saveAgendamento(@ModelAttribute("agendamento") Agendamento agendamento,
							  	  Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		String url = NavigatorUtils.getUrlToBack();

		agendamento.setSalaoAgendamento(salao);
		agendamento.setSituacao(SituacaoAgendamento.AGENDADO);
		
		ControllerHelper.setEditMode(model, true);
		
		agendamentoService.saveAgendamento(agendamento);
		
		if (url.contains("home") || url.contains("cliente") || url.contains("funcionario") || url.contains("servico")) {
			return agendamento(new AgendamentoFilter(), model);
		} else {
			return url;
		}
		
	}
	
	@PostMapping(path = "/cancel")
	public String cancelAgendamento(@RequestParam("idAgendCancel") Integer idAgendamento,
								    Model model,
								    HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "incagendamento");
		String url = NavigatorUtils.getUrlToBack();
		
		Agendamento agendamento = agendamentoRepository.findBySalaoAgendamentoAndIdAgendamento(salao, idAgendamento);
		agendamento.setSituacao(SituacaoAgendamento.CANCELADO);
		
		agendamentoService.saveAgendamento(agendamento);
		
		return url;
	}
	
	@PostMapping(path = "/confirm")
	public String confirmAgendamento(@RequestParam("idAgendConfirm") Integer idAgendamento,
								     Model model,
								     HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "incagendamento");
		String url = NavigatorUtils.getUrlToBack();
		
		Agendamento agendamento = agendamentoRepository.findBySalaoAgendamentoAndIdAgendamento(salao, idAgendamento);
		agendamento.setSituacao(SituacaoAgendamento.REALIZADO);
		
		agendamentoService.confirmAgendamento(agendamento);
		
		return url;
	}
	
	@PostMapping(path = "/notattend")
	public String notAttendAgendamento(@RequestParam("idAgendNotAttend") Integer idAgendamento,
								     Model model,
								     HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();

		NavigatorUtils.setUrlToBack(request, "incagendamento");
		String url = NavigatorUtils.getUrlToBack();
		
		Agendamento agendamento = agendamentoRepository.findBySalaoAgendamentoAndIdAgendamento(salao, idAgendamento);
		agendamento.setSituacao(SituacaoAgendamento.NAO_COMPARECEU);
		
		agendamentoService.saveAgendamento(agendamento);
		
		return url;
	}
	
}