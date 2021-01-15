package br.com.ricardoianni.inovacaoapp.domain.produto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "unidademedida")
public class UnidadeMedida implements Serializable {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idunidademedida")
	private Integer idUnidadeMedida;
	
	@NotNull
	private String descricao;
	
	private String abreviacao;
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "unidadeMedida")
	private Set<Produto> produtoMedida = new HashSet<>(0);

}