package br.com.ricardoianni.inovacaoapp.domain.pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import org.springframework.format.annotation.DateTimeFormat;

import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true )
@Entity
public class Cliente extends Pessoa implements Comparable<Cliente> {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcliente")
	private Integer idCliente;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoCliente;
	
	@Column(length = 11, nullable = false)
	private String cpf;
	
	public String getFormattedCpf() {
		if (!cpf.isEmpty()) {
			return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		}
		
		return cpf;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "datanascimento")
	private LocalDate dataNascimento;
	
	public String getDataNascimentoFormated() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", FormatUtils.LOCALE_BRAZIL);
		LocalDate data = dataNascimento;
		
		return data.format(formatter);
	}
	
	public String getAniversario(LocalDate data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM", FormatUtils.LOCALE_BRAZIL);
		
		return data.format(formatter);
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "clienteTelefone")
	private Set<Telefone> telefoneCliente = new HashSet<>(0);

	public String telefone(int i) {
		Telefone telefone = (Telefone) telefoneCliente.toArray()[i];
		String retorno = telefone.getTelefone();
		
		return retorno;
	}
	
	@OneToMany(mappedBy = "clienteEndereco")
	private Set<Endereco> enderecoCliente = new HashSet<>(0);

	@OneToMany(mappedBy = "clienteEmail", fetch = FetchType.EAGER)
	private Set<EnderecoEmail> emailCliente = new HashSet<>(0);

	public String email(int i) {
		EnderecoEmail email = (EnderecoEmail) emailCliente.toArray()[i];
		String retorno = email.getEndEmail();
		
		return retorno;
	}
	
	@OneToMany(mappedBy = "clienteAgendamento")
	private Set<Agendamento> agendamentoCliente = new HashSet<>(0);

	@Override
	public int compareTo(Cliente o) {
		return this.getAniversario(this.dataNascimento).compareTo(o.getAniversario(o.dataNascimento));
	}
	
}