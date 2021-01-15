package br.com.ricardoianni.inovacaoapp.domain.agendamento;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;
import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Agendamento implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoAgendamento;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idagendamento")
	private Integer idAgendamento;
	
	@ManyToOne
	@JoinColumn(name = "idfuncionario")
	private Funcionario funcionarioAgendamento;
	
	@ManyToOne
	@JoinColumn(name = "idcliente")
	private Cliente clienteAgendamento;
	
	@ManyToOne
	@JoinColumn(name = "idservico")
	private Servico servicoAgendamento;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data")
	private LocalDate dataAgendamento;
	
	public String getDateFormated(LocalDate data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", FormatUtils.LOCALE_BRAZIL);
		
		return data.format(formatter);
	}
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "hora")
	private LocalTime horaAgendamento;
	
	@Enumerated(EnumType.ORDINAL)
	private SituacaoAgendamento situacao;

}
