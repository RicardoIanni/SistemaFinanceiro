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
public class Fornecedor extends Pessoa {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfornecedor")
	private Integer idFornecedor;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoFornecedor;
	
	@Column(length = 11)
	private String cpf;
	
	public String getFormattedCpf() {
		if (!cpf.isEmpty()) {
			return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		}
		
		return cpf;
	}
	
	@Column(length = 14)
	private String cnpj;
	
	public String getFormattedCnpj() {
		if (!cnpj.isEmpty()) {
			return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-"+ cnpj.substring(12);
		}
		
		return cnpj;
	}
	
	private String pf_pj;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fornecedorTelefone")
	private Set<Telefone> telefoneFornecedor = new HashSet<>(0);

	public String telefone(int i) {
		Telefone telefone = (Telefone) telefoneFornecedor.toArray()[i];
		String retorno = telefone.getTelefone();
		
		return retorno;
	}

	@OneToMany(mappedBy = "fornecedorEndereco")
	private Set<Endereco> enderecoFornecedor= new HashSet<>(0);

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fornecedorEmail")
	private Set<EnderecoEmail> emailFornecedor = new HashSet<>(0);

	public String email(int i) {
		EnderecoEmail email = (EnderecoEmail) emailFornecedor.toArray()[i];
		String retorno = email.getEndEmail();
		
		return retorno;
	}

}
