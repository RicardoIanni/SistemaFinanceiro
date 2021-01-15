package br.com.ricardoianni.inovacaoapp.domain.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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

import org.springframework.format.annotation.DateTimeFormat;

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
public class Conta implements Serializable {
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "idsalao")
	private Salao salaoConta;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idconta")
	private Integer idConta;
	
	private String descricao;
	
	@Column(name = "saldoinicial")
	private BigDecimal saldoInicial;
	
	public String strSaldoInicial() {
		return FormatUtils.formatNumber(saldoInicial);
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	
	private Boolean padrao;
	
	private Boolean ativo;
	
	@OneToMany(mappedBy = "contaPagamento")
	private Set<Pagamento> pagamentoConta = new HashSet<>(0);
	
	@OneToMany(mappedBy = "contaRecebimento")
	private Set<Recebimento> recebimentoConta = new HashSet<>(0);
	
	@OneToMany(mappedBy = "contaSaldo")
	private Set<Saldo> saldoConta = new HashSet<>(0);
	
	@ManyToOne
	@JoinColumn(name = "idtipoconta")
	private TipoConta tipoConta;
}
