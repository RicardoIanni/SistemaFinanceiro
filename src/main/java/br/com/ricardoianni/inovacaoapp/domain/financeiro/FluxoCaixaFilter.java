package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import lombok.Data;

@Data
public class FluxoCaixaFilter {

	private Integer idConta;
	
	private Integer numeroMes;
	
	private Integer ano;	
	
}
