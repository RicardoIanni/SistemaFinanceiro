package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface SaldoRepository extends JpaRepository<Saldo, Integer> {

	public List<Saldo> findBySalaoSaldoAndNumMesAndNumAno(Salao salaoSaldo, Integer numMes, Integer numAno);
	
	public List<Saldo> findBySalaoSaldoAndContaSaldo(Salao salaoSaldo, Conta contaSaldo);
	
	public List<Saldo> findBySalaoSaldoAndContaSaldoAndNumMesLessThanEqualAndNumAnoLessThanEqualOrderByNumAnoDescNumMesDesc(Salao salaoSaldo, Conta contaSaldo, Integer numMes, Integer numAno);
	
}