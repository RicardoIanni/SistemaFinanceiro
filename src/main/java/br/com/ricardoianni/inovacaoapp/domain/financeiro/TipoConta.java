package br.com.ricardoianni.inovacaoapp.domain.financeiro;

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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tipoconta")
public class TipoConta implements Serializable {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtipoconta")
	private Integer idTipoConta;
	
	private String descricao;
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "tipoConta")
	private Set<Conta> Conta = new HashSet<>(0);
	
}