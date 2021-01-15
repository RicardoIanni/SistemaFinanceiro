package br.com.ricardoianni.inovacaoapp.domain.servico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;
import br.com.ricardoianni.inovacaoapp.utils.ValidUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Servico implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoServico;

	@EqualsAndHashCode.Include 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idservico")
	private Integer idServico;
	
	@NotBlank(message = ValidUtils.CAMPO_OBRIGATORIO_MSG)
	private String descricao;
	
	private BigDecimal valor;
	
	public String strValor() {
		return FormatUtils.formatNumber(valor);
	}
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "servicoPacote")
	private Set<PacoteServico> pacoteServico = new HashSet<>(0);

	@OneToMany(mappedBy = "servicoMaterial")
	private Set<Material> materialServico = new HashSet<>(0);
	
	@OneToMany(mappedBy = "servicoAgendamento")
	private Set<Agendamento> agendamentoServico = new HashSet<>(0);
	
	}