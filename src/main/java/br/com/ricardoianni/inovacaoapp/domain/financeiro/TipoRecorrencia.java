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
@Table(name = "tiporecorrencia")
public class TipoRecorrencia implements Serializable {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtiporecorrencia")
	private Integer idTipoRecorrencia;
	
	private String descricao;
	
	private Integer dias;
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "recorrenciaPagamento")
	private Set<Pagamento> pagamentoRecorrencia = new HashSet<>(0);
	
	@OneToMany(mappedBy = "recorrenciaRecebimento")
	private Set<Recebimento> recebimentoRecorrencia = new HashSet<>(0);
	
}
