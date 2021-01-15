package br.com.ricardoianni.inovacaoapp.domain.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.ValidUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "email")
public class EnderecoEmail {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoEmail;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idemail")
	private Integer idEmail;
	
	@Email(message = ValidUtils.EMAIL_INVALIDO_MSG)
	@Column(name = "email")
	private String endEmail;
	
	@ManyToOne
	@JoinColumn(name = "idtipoemail")
	private TipoEmail tipoEmail;
	
	@ManyToOne
	@JoinColumn(name = "idcliente", nullable = true)
	private Cliente clienteEmail;
	
	@ManyToOne
	@JoinColumn(name = "idfuncionario", nullable = true)
	private Funcionario funcionarioEmail;
	
	@ManyToOne
	@JoinColumn(name = "idfornecedor", nullable = true)
	private Fornecedor fornecedorEmail;
	
}
