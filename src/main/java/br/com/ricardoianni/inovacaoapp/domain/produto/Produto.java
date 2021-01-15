package br.com.ricardoianni.inovacaoapp.domain.produto;

import java.io.Serializable;
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
import javax.validation.constraints.NotBlank;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.servico.Material;
import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;
import br.com.ricardoianni.inovacaoapp.utils.ValidUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoProduto;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idprotudo")
	private Integer idProduto;
	
	@NotBlank(message = ValidUtils.CAMPO_OBRIGATORIO_MSG)
	private String descricao;
	
	private Double estoque;
	
	public String strEstoque() {
		return FormatUtils.formatNumber(estoque);
	}
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "produtoCompra")
	private Set<ProdutoCompra> compra = new HashSet<>(0);
	
	@OneToMany(mappedBy = "produtoVenda")
	private Set<ProdutoVenda> venda = new HashSet<>(0);
	
	@ManyToOne
	@JoinColumn(name = "idunidademedida")
	private UnidadeMedida unidadeMedida;
	
	public String abreviacaoUnidadeMedida() {
		if (unidadeMedida != null) {
			return unidadeMedida.getAbreviacao();
		}
		
		return null;
	}
	
	@OneToMany(mappedBy = "produtoMaterial")
	private Set<Material> material = new HashSet<>(0);
	
}