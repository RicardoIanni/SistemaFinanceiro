package br.com.ricardoianni.inovacaoapp.domain.telefone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.ValidUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Telefone implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoTelefone;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtelefone")
	private Integer idTelefone;
	
	@ManyToOne
	@JoinColumn(name = "idtipotelefone")
	private TipoTelefone tipoTelefone;
	
	@Size(max = 2, message = ValidUtils.TAMANHO_MAXIMO_MSG)
	@Column(length = 2, nullable = false)
	private String ddd;
	
	@Size(max = 9, message = ValidUtils.TAMANHO_MAXIMO_MSG)
	@Column(name = "numtelefone", length = 9, nullable = false)
	private String numTelefone;
	
	public String getTelefone() {
		String telefone;
		
		telefone = "(" + ddd + ") ";
		
		if (numTelefone.trim().length() == 8) {
			telefone = telefone + numTelefone.substring(0, 4) + "-" + numTelefone.substring(4);
		} else if (numTelefone.trim().length() == 9) {
			telefone = telefone + numTelefone.substring(0, 5) + "-" + numTelefone.substring(5);			
		}
		
		return telefone;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcliente", nullable = true)
	private Cliente clienteTelefone;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idfuncionario", nullable = true)
	private Funcionario funcionarioTelefone;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idfornecedor", nullable = true)
	private Fornecedor fornecedorTelefone;

}