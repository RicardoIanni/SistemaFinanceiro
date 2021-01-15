package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pacoteservicocliente")
public class PacoteServicoCliente implements Serializable {

	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoPctCliente;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpctcliente")
	private Integer idPctCliente;
	
	@ManyToOne
	@JoinColumn(name = "idcliente")
	private Cliente clientePctCliente;
	
	@ManyToOne
	@JoinColumn(name = "idpacote")
	private PacoteServico pacoteServicoPctCliente;
	
	private Integer saldo;
	
}