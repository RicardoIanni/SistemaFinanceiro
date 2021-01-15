package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pacoteservico")
public class PacoteServico implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoPacoteServico;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpacote")
	private Integer idPacote;
	
	private Integer quantidade;
	
	private BigDecimal valor;
	
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "idservico")
	private Servico servicoPacote;
}
