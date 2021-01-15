package br.com.ricardoianni.inovacaoapp.domain.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoEndereco;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idendereco")
	private Integer idEndereco;
	
	@ManyToOne
	@JoinColumn(name = "idtipoendereco")
	private TipoEndereco tipoEndereco;
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	@Size(max = 2, message = ValidUtils.TAMANHO_MAXIMO_MSG)
	private String estado;
	
	@Column(length = 8, nullable = false)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "idcliente", nullable = true)
	private Cliente clienteEndereco;
	
	@ManyToOne
	@JoinColumn(name = "idfuncionario", nullable = true)
	private Funcionario funcionarioEndereco;
	
	@ManyToOne
	@JoinColumn(name = "idfornecedor", nullable = true)
	private Fornecedor fornecedorEndereco;
	
}
