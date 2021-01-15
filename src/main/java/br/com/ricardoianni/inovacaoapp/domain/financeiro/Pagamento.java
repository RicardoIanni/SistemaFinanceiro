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

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Pagamento extends FluxoCaixa {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoPagamento;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpagamento")
	private Integer idPagamento;
	
	@ManyToOne
	@JoinColumn(name = "idfornecedor")
	private Fornecedor fornecedorPagamento;
	
	@ManyToOne
	@JoinColumn(name = "idtipopagamento")
	private TipoPagamento tipoPagamento;
	
	@ManyToOne
	@JoinColumn(name = "idconta")
	private Conta contaPagamento;
		
	@ManyToOne
	@JoinColumn(name = "idtiporecorrencia")
	private TipoRecorrencia recorrenciaPagamento;
	
	@ManyToOne
	@JoinColumn(name = "idrecorente")
	private Pagamento recorrentePagamento;
	
	@OneToMany(mappedBy = "recorrentePagamento")
	private Set<Pagamento> pagamentoRecorrente = new HashSet<>(0);
	
}
