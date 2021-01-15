package br.com.ricardoianni.inovacaoapp.domain.pessoa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	public List<Cliente> findBySalaoClienteOrderByNome
								(Salao salaoCliente);
	
	public Cliente findBySalaoClienteAndIdCliente
								(Salao salaoCliente, 
								 Integer idCliente);
	
	public List<Cliente> findBySalaoClienteAndNomeContainingOrderByNome
								(Salao salaoCliente, 
								 String text);
	
	public List<Cliente> findBySalaoClienteAndDataNascimentoBetweenOrderByDataNascimentoAscNomeAsc
								(Salao salaoCliente, 
								 LocalDate dataInicial,
								 LocalDate dataFinal);
	
	@Query("SELECT c FROM Cliente c WHERE c.salaoCliente = ?1 AND Month(c.dataNascimento) = ?2")
	public List<Cliente> findBySalaoAndMes(Salao salaoCliente, Integer mes);

}
