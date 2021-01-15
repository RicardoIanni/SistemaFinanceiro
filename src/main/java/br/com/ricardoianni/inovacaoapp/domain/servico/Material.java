package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.ricardoianni.inovacaoapp.domain.produto.Produto;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Material implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoMaterial;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmaterial")
	private Integer idMaterial;
	
	private Double quantidade;
	
	public String strQuantidade() {
		return FormatUtils.formatNumber(quantidade);
	}
	
	@ManyToOne
	@JoinColumn(name = "idproduto")
	private Produto produtoMaterial;
	
	@ManyToOne
	@JoinColumn(name = "idservico")
	private Servico servicoMaterial;

}