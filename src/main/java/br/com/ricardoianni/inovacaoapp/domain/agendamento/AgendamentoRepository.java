package br.com.ricardoianni.inovacaoapp.domain.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoAndFuncionarioAgendamentoAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Cliente clienteAgendamento, 
									 Funcionario funcionarioAgendamento, 
									 Servico servicoAgendamento);
	
	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoAndFuncionarioAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Cliente clienteAgendamento, 
									 Funcionario funcionarioAgendamento);
	
	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Cliente clienteAgendamento, 
									 Servico servicoAgendamento);

	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndFuncionarioAgendamentoAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Funcionario funcionarioAgendamento, 
									 Servico servicoAgendamento);
	
	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndClienteAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Cliente clienteAgendamento);

	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndServicoAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Servico servicoAgendamento);

	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualAndFuncionarioAgendamentoOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento, 
									 Funcionario funcionarioAgendamento);

	public List<Agendamento> findBySalaoAgendamentoAndDataAgendamentoGreaterThanEqualAndHoraAgendamentoGreaterThanEqualOrderByDataAgendamentoAscHoraAgendamentoAsc
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento, 
									 LocalTime horaAgendamento);
	
	public List<Agendamento> findFirst7BySalaoAgendamentoAndDataAgendamentoAndHoraAgendamentoGreaterThanEqualOrderByHoraAgendamento
									(Salao salaoAgendamento, 
									 LocalDate dataAgendamento,
									 LocalTime horaAgendamento);
	
	public Agendamento findBySalaoAgendamentoAndIdAgendamento(Salao salaoAgendamento, Integer idAgendamento);

}
