package br.com.ricardoianni.inovacaoapp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.agendamento.AgendamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Conta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.ContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Recebimento;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

@Service
public class AgendamentoService {

	@Autowired
	private FinanceiroService financeiroService;
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	public void saveAgendamento(Agendamento agendamento) {
		
		agendamentoRepository.save(agendamento);
		
	}
	
	public void confirmAgendamento(Agendamento agendamento) {
		
		Salao salao = agendamento.getSalaoAgendamento();
		Conta conta;
		String descricao;
		
		this.saveAgendamento(agendamento);
		
		descricao = agendamento.getServicoAgendamento().getDescricao().trim() + " - " + agendamento.getDateFormated(agendamento.getDataAgendamento()).toString().trim() + " - " + agendamento.getHoraAgendamento().toString().trim();
		
		Recebimento recebimento = new Recebimento();

		try {
			conta = contaRepository.findBySalaoContaAndPadrao(salao, true).get(0);
			recebimento.setContaRecebimento(conta);

		} catch (Exception e) {
			conta = new Conta();
		}	
		
		recebimento.setSalaoRecebimento(salao);
		recebimento.setDescricao(descricao);
		recebimento.setClienteRecebimento(agendamento.getClienteAgendamento());
		recebimento.setVencimento(agendamento.getDataAgendamento());
		recebimento.setValor(agendamento.getServicoAgendamento().getValor());
		recebimento.setRecorrente(false);
		recebimento.setRealizado(true);
		
		financeiroService.saveRecebimento(recebimento);
		
	}
}
