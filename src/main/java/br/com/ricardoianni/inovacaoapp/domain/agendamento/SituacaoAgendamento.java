
package br.com.ricardoianni.inovacaoapp.domain.agendamento;

public enum SituacaoAgendamento {

	AGENDADO,
	CANCELADO,
	REALIZADO,
	NAO_COMPARECEU;

	@Override
	public String toString() {
		
		String text;
		
		if (this.name() == "AGENDADO" ) {
			text = "Agendado";
		} else if (this.name() == "CANCELADO" ) {
			text = "Cancelado";
		} else if (this.name() == "REALIZADO" ) {
			text = "Realizado";
		} else if (this.name() == "NAO_COMPARECEU" ) {
			text = "Não Compareceu";
		} else {
			text = "";
		}
		
		return text;
	}

	
	
}
