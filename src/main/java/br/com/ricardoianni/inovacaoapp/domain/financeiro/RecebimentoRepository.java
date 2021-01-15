package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {

	public List<Recebimento> findBySalaoRecebimentoOrderByVencimento(Salao salaoRecebimento);
	
	public List<Recebimento> findBySalaoRecebimentoAndContaRecebimentoOrderByVencimento(Salao salaoRecebimento, Conta contaRecebimento);
	
	public Recebimento findBySalaoRecebimentoAndIdRecebimento(Salao salaoRecebimento, Integer idRecebimento);
	
	@Query("SELECT r FROM Recebimento r WHERE r.salaoRecebimento = ?1 AND r.vencimento BETWEEN ?2 AND ?3")
	public List<Recebimento> findBySalaoAndMes(Salao salaoRecebimento, LocalDate dataInicial, LocalDate dataFinal);
	
	@Query("SELECT r FROM Recebimento r WHERE r.salaoRecebimento = ?1 AND r.contaRecebimento = ?2 AND r.vencimento BETWEEN ?3 AND ?4")
	public List<Recebimento> findBySalaoAndContaAndMes(Salao salaoRecebimento, Conta conta, LocalDate dataInicial, LocalDate dataFinal);
	
}
