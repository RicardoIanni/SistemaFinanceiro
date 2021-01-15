package br.com.ricardoianni.inovacaoapp.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.financeiro.Conta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.ContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Pagamento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.PagamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Recebimento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.RecebimentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Saldo;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.SaldoRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private SaldoRepository saldoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private RecebimentoRepository recebimentoRepository;

	public void saveConta(Conta conta) {
		Salao salao = SecurityUtils.loggedSalao();
		
		List<Conta> contas = contaRepository.findBySalaoContaAndPadrao(salao, true);

		for (Conta contaAux : contas) {
			contaAux.setPadrao(false);
			contaRepository.save(contaAux);
		}
		
		contaRepository.save(conta);
		
		Saldo saldo = null;
		
		List<Saldo> saldos = saldoRepository.findBySalaoSaldoAndContaSaldo(salao, conta);
		
		if (saldos.size() == 0) {
			
			saldo = new Saldo();
			
			saldo.setSalaoSaldo(salao);
			saldo.setContaSaldo(conta);
			saldo.setNumMes(conta.getData().getMonthValue());
			saldo.setNumAno(conta.getData().getYear());
			saldo.setSaldo(conta.getSaldoInicial());
			
		} else {
		
			List<Pagamento> pagamentos = pagamentoRepository.findBySalaoPagamentoAndContaPagamentoOrderByVencimento(salao, conta);
			List<Recebimento> recebimentos = recebimentoRepository.findBySalaoRecebimentoAndContaRecebimentoOrderByVencimento(salao, conta);
		
			if (pagamentos.size() == 0 && recebimentos.size() == 0) {
				saldo = saldos.get(0);
				saldo.setSaldo(conta.getSaldoInicial());
			}
			
		}
		
		if (saldo != null) {
			saldoRepository.save(saldo);
		}
		
	}

	public void deleteConta(Conta conta) {
		Salao salao = SecurityUtils.loggedSalao();
		List<Saldo> saldos = saldoRepository.findBySalaoSaldoAndContaSaldo(salao, conta);
		
		for (Saldo saldo : saldos) {
			saldoRepository.deleteById(saldo.getIdSaldo());
		}
		
		contaRepository.deleteById(conta.getIdConta());
		
	}
}