package br.com.ricardoianni.inovacaoapp.domain.pessoa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
	
	public List<Funcionario> findBySalaoFuncionarioOrderByNome(Salao salaoFuncionario);
	
	public List<Funcionario> findBySalaoFuncionarioAndNomeContainingOrderByNome(Salao salaoFuncionario, String text);
	
	public Funcionario findBySalaoFuncionarioAndIdFuncionario(Salao salaoFuncionario, Integer idFuncionario);

}
