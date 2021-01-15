package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Saldo implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoSaldo;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsaldo")
	private Integer idSaldo;

	private Integer numMes;
	
	private Integer numAno;
	
	private BigDecimal saldo;
	
	public String strSaldo(BigDecimal saldo) {
		return FormatUtils.formatNumber(saldo.abs());
	}
	
	@ManyToOne
	@JoinColumn(name = "idconta")
	private Conta contaSaldo;
	
}