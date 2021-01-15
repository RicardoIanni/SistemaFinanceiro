package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.ricardoianni.inovacaoapp.dashboard.Dashboard;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.AgendamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Controller
public class LoggedUserController {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping(path = "/admin/home")
	public String loggedAdminUser(Model model) {
		Salao salao = SecurityUtils.loggedSalao();
		Dashboard home = new Dashboard();
		List<Agendamento> agendamentos = home.agendamentoDia(agendamentoRepository, salao, LocalDate.now());
		
		model.addAttribute("agendamentos", agendamentos);
		
		List<Cliente> aniversarios = home.aniversarioMes(clienteRepository, salao);
		
		model.addAttribute("aniversarios", aniversarios);
		
		return "home";
	}
	
}
