package br.com.ricardoianni.inovacaoapp.application.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardoianni.inovacaoapp.domain.financeiro.Pagamento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.PagamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Recebimento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.RecebimentoRepository;

@Service
public class FinanceiroService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private RecebimentoRepository recebimentoRepository;
	
	public void savePagamento(Pagamento pagamento) {
		
		if (pagamento.getRecorrente()) { 
			Pagamento[] pagamentos = new Pagamento[pagamento.getQtdRecorrencia()];
			LocalDate data = pagamento.getVencimento(); 
			Integer dias = pagamento.getRecorrenciaPagamento().getDias();

			for (int i = 0; i < pagamentos.length; i++) {
				pagamentos[i] = new Pagamento();
				
				pagamentos[i].setSalaoPagamento(pagamento.getSalaoPagamento());
				pagamentos[i].setContaPagamento(pagamento.getContaPagamento());
				pagamentos[i].setDescricao(pagamento.getDescricao() + " (" + (i+1) + "/" + pagamentos.length + ")");
				pagamentos[i].setFornecedorPagamento(pagamento.getFornecedorPagamento());
				pagamentos[i].setVencimento(data.plusDays(i * dias));
				pagamentos[i].setValor(pagamento.getValor());

				pagamentos[i].setRecorrente(pagamento.getRecorrente());
				pagamentos[i].setRecorrentePagamento(pagamentos[0]);
				pagamentos[i].setRecorrenciaPagamento(pagamento.getRecorrenciaPagamento());
				pagamentos[i].setQtdRecorrencia(pagamento.getQtdRecorrencia());
				
				pagamentoRepository.save(pagamentos[i]);
			}
		  
		  } else {
			  
			pagamento.setQtdRecorrencia(1);
		 
			pagamentoRepository.save(pagamento);
		}
		
	}
	
	public void deletePagamento(Pagamento pagamento) {
		
		pagamentoRepository.deleteById(pagamento.getIdPagamento());
		
	}

	public void saveRecebimento(Recebimento recebimento) {
		
		if (recebimento.getRecorrente()) { 
			Recebimento[] recebimentos = new Recebimento[recebimento.getQtdRecorrencia()];
			LocalDate data = recebimento.getVencimento(); 
			Integer dias = recebimento.getRecorrenciaRecebimento().getDias();

			for (int i = 0; i < recebimentos.length; i++) {
				recebimentos[i] = new Recebimento();
				
				recebimentos[i].setSalaoRecebimento(recebimento.getSalaoRecebimento());
				recebimentos[i].setContaRecebimento(recebimento.getContaRecebimento());
				recebimentos[i].setDescricao(recebimento.getDescricao() + " (" + (i+1) + "/" + recebimentos.length + ")");
				recebimentos[i].setClienteRecebimento(recebimento.getClienteRecebimento());
				recebimentos[i].setVencimento(data.plusDays(i * dias));
				recebimentos[i].setValor(recebimento.getValor());

				recebimentos[i].setRecorrente(recebimento.getRecorrente());
				recebimentos[i].setRecorrenteRecebimento(recebimentos[0]);
				recebimentos[i].setRecorrenciaRecebimento(recebimento.getRecorrenciaRecebimento());
				recebimentos[i].setQtdRecorrencia(recebimento.getQtdRecorrencia());
				
				recebimentoRepository.save(recebimentos[i]);
			}
		  
		  } else {
			  
			recebimento.setQtdRecorrencia(1);
		 
			recebimentoRepository.save(recebimento);
		}
		
	}
	
	public void deleteRecebimento(Recebimento recebimento) {
		
		recebimentoRepository.deleteById(recebimento.getIdRecebimento());
		
	}

}
