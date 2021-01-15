package br.com.ricardoianni.inovacaoapp.domain.email;

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
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tipoemail")
public class TipoEmail implements Serializable {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtipoemail")
	private Integer idTipoEmail;
	
	private String descricao;
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "tipoEmail")
	private Set<EnderecoEmail> email = new HashSet<>(0);
		
}