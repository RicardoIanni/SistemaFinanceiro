package br.com.ricardoianni.inovacaoapp.domain.endereco;

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

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tipoendereco")
public class TipoEndereco {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtipoendereco")
	private Integer idTipoEndereco;
	
	private String descricao;
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "tipoEndereco")
	private Set<Endereco> endereco = new HashSet<>(0);

}
