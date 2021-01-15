package br.com.ricardoianni.inovacaoapp.dashboard;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.AgendamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public class Dashboard {

	public List<Agendamento> agendamentoDia(AgendamentoRepository repository, Salao salao, LocalDate data) {

		ZoneId z = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(-3));
		int h = LocalTime.now(z).getHour()-3;
		
		if (h < 0) {
			h = h + 24;
			
			data = data.minusDays(1);
		}
		
		LocalTime hora = LocalTime.of(h, 0);
		
		List<Agendamento> agendamentos = repository.findFirst7BySalaoAgendamentoAndDataAgendamentoAndHoraAgendamentoGreaterThanEqualOrderByHoraAgendamento
														(salao, data, hora);
		
		for (Agendamento agendamento : agendamentos) {
			agendamento.getFuncionarioAgendamento().setNome(agendamento.getFuncionarioAgendamento().getNome().split(" ")[0]);
			
			String[] clienteNomeCompleto = agendamento.getClienteAgendamento().getNome().split(" ");
			String clienteNome = clienteNomeCompleto[0];
			
			if (clienteNomeCompleto.length > 1) {
				clienteNome += " " + clienteNomeCompleto[clienteNomeCompleto.length - 1];
			}
			
			agendamento.getClienteAgendamento().setNome(clienteNome);
		}
		
		return agendamentos;
	}
	
	public List<Cliente> aniversarioMes(ClienteRepository repository, Salao salao) {
		List<Cliente> clientes = repository.findBySalaoAndMes(salao, LocalDate.now().getMonthValue());

		for (Cliente cliente: clientes) {
			String[] clienteNomeCompleto = cliente.getNome().split(" ");
			String clienteNome = clienteNomeCompleto[0];
			
			if (clienteNomeCompleto.length > 1) {
				clienteNome += " " + clienteNomeCompleto[clienteNomeCompleto.length - 1];
			}
			
			cliente.setNome(clienteNome);
		}
		
		Collections.sort(clientes);
		
		return clientes;
	}
}
