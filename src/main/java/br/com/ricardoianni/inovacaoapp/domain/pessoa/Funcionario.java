package br.com.ricardoianni.inovacaoapp.domain.pessoa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Funcionario extends Pessoa {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfuncionario")
	private Integer idFuncionario;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoFuncionario;
	
	@Column(length = 11, nullable = false)
	private String cpf;
	
	public String getFormattedCpf() {
		if (!cpf.isEmpty()) {
			return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		}
		
		return cpf;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "funcionarioTelefone")
	private Set<Telefone> telefoneFuncionario = new HashSet<>(0);

	public String telefone(int i) {
		Telefone telefone = (Telefone) telefoneFuncionario.toArray()[i];
		String retorno = telefone.getTelefone();
		
		return retorno;
	}

	@OneToMany(mappedBy = "funcionarioEndereco")
	private Set<Endereco> enderecoFuncionario = new HashSet<>(0);

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "funcionarioEmail")
	private Set<EnderecoEmail> emailFuncionario = new HashSet<>(0);

	public String email(int i) {
		EnderecoEmail email = (EnderecoEmail) emailFuncionario.toArray()[i];
		String retorno = email.getEndEmail();
		
		return retorno;
	}
	
	@OneToMany(mappedBy = "funcionarioAgendamento")
	private Set<Agendamento> agendamentoFuncionario = new HashSet<>(0);
		
}
