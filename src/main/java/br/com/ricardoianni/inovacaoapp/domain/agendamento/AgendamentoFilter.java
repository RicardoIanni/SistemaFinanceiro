package br.com.ricardoianni.inovacaoapp.domain.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AgendamentoFilter {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	private Integer idCliente;
	
	private Integer idFuncionario;
	
	private Integer idServico;
	
}
