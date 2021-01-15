package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Recebimento extends FluxoCaixa {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoRecebimento;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrecebimento")
	private Integer idRecebimento;
	
	@ManyToOne
	@JoinColumn(name = "idcliente")
	private Cliente clienteRecebimento;
	
	@ManyToOne
	@JoinColumn(name = "idtiporecebimento")
	private TipoPagamento tipoRecebimento;
	
	@ManyToOne
	@JoinColumn(name = "idconta")
	private Conta contaRecebimento;
		
	@ManyToOne
	@JoinColumn(name = "idtiporecorrencia")
	private TipoRecorrencia recorrenciaRecebimento;
	
	@ManyToOne
	@JoinColumn(name = "idrecorrente")
	private Recebimento recorrenteRecebimento;
	
	@OneToMany(mappedBy = "recorrenteRecebimento")
	private Set<Recebimento> recebimentoRecorrente = new HashSet<>(0);
	
	public String getDescricaoConta() {
		if (this.contaRecebimento != null) {
			return this.contaRecebimento.getDescricao();
		}
		
		return null;
	}
}
