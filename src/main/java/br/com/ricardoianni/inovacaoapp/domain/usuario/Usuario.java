package br.com.ricardoianni.inovacaoapp.domain.usuario;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.ricardoianni.inovacaoapp.domain.pessoa.Pessoa;
import br.com.ricardoianni.inovacaoapp.utils.StringUtils;
import br.com.ricardoianni.inovacaoapp.utils.ValidUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
public class Usuario extends Pessoa {
	
	@NotBlank(message = ValidUtils.CAMPO_OBRIGATORIO_MSG)
	@Size(max = 15, message = ValidUtils.TAMANHO_MAXIMO_MSG)
	@Column(length = 15, nullable = false)
	private String user;
	
	@NotBlank(message = ValidUtils.CAMPO_OBRIGATORIO_MSG)
	@Column(length = 80, nullable = false)
	private String password;

	public void encryptPassword() {
		this.password = StringUtils.encrypt(this.password);
	}
	
}
