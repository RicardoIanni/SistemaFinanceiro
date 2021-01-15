package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

	public List<Pagamento> findBySalaoPagamentoOrderByVencimento(Salao salaoPagamento);
	
	public List<Pagamento> findBySalaoPagamentoAndContaPagamentoOrderByVencimento(Salao salaoPagamento, Conta contaPagamento);
	
	public Pagamento findBySalaoPagamentoAndIdPagamento(Salao salaoPagamento, Integer idPagamento);
	
	public List<Pagamento> findBySalaoPagamentoAndVencimentoBetweenOrderByVencimento(Salao salaoPagamento, LocalDate dataInicial, LocalDate dataFinal);
	
	public List<Pagamento> findBySalaoPagamentoAndContaPagamentoAndVencimentoBetweenOrderByVencimento(Salao salaoPagamento, Conta contaPagamento, LocalDate dataInicial, LocalDate dataFinal);
	
}
