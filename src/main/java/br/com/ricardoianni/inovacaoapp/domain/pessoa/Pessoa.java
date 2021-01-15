package br.com.ricardoianni.inovacaoapp.domain.pessoa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import br.com.ricardoianni.inovacaoapp.utils.ValidUtils;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@MappedSuperclass
public class Pessoa implements Serializable {

	@NotBlank(message = ValidUtils.CAMPO_OBRIGATORIO_MSG)
	private String nome;
	
}
