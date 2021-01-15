package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class FluxoCaixa implements Serializable, Comparable<FluxoCaixa> {

	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate vencimento;
	
	private BigDecimal valor;
	
	public String strValor() {
		return FormatUtils.formatNumber(valor.abs());
	}
	
	private Boolean recorrente;
	
	@Column(name = "qtrrecorrencia")
	private Integer qtdRecorrencia;
	
	private Boolean realizado;
	
	public static String getDateFormated(LocalDate data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", FormatUtils.LOCALE_BRAZIL);
		
		return data.format(formatter);
	}

	@Override
	public int compareTo(FluxoCaixa o) {
		if (this.getVencimento().isBefore(o.getVencimento())) {
			return -1;
		}
		
		if (this.getVencimento().isAfter(o.getVencimento())) {
			return 1;
		}
		
		return 0;
	}
	
}