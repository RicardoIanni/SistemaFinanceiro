function isNumberKey(evt) {
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	
	if (charCode >= 48 && charCode <= 57 || charCode >= 96 && charCode <= 105 || charCode >= 37 && charCode <= 40 || charCode <= 31 || charCode == 46 || charCode == 116) {	
		return true;
	}
	
	return false;
}

function isLetterKey(evt) {
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	
	if (charCode >= 65 && charCode <= 90 || charCode <= 31) {
		return true;
	}
	
	return false;
}

function pjSelected() {
	var elementInput_01 = document.getElementById("cpf");
	var elementInput_02 = document.getElementById('cnpj');
	
	elementInput_01.style.visibility = 'hidden';
	elementInput_02.style.visibility = 'visible';
}

function pfSelected() {
	var elementInput_01 = document.getElementById("cpf");
	var elementInput_02 = document.getElementById('cnpj');
	
	elementInput_01.style.visibility = 'visible';
	elementInput_01.style.left = elementInput_02.offsetLeft + 'px'
	elementInput_01.style.top = elementInput_02.offsetTop + 'px'
	elementInput_02.style.visibility = 'hidden';

}

function recorrenteChange(checkbox) {
	var elementTd_01 = document.getElementById("recorrenteSlct");
	var elementTd_02 = document.getElementById("recorrenteQtd");
	var elementTd_03 = document.getElementById("realizadoChk");
	
	if (checkbox.checked) {
		elementTd_01.style.visibility = 'visible';
		elementTd_02.style.left = elementTd_01.offsetLeft + 124 + 'px';
		elementTd_02.style.visibility = 'visible';
		elementTd_03.style.left = '229px';
	} else {
		elementTd_01.style.visibility = 'hidden';
		elementTd_02.style.visibility = 'hidden';
		elementTd_03.style.left = '0px';
	}
}

function disableRecorrencia() {
	if (!document.getElementById("recorrente").checked) {
		document.getElementById("tipoRecorrencia").disabled = true;
		document.getElementById("recorrencia").disabled = true;
	} else {
		document.getElementById("tipoRecorrencia").disabled = false;
		document.getElementById("recorrencia").disabled = false;
	}
}

function disableRealizado(formID) {
	var x = document.getElementById(formID);
	var y = document.getElementById("realizado").checked
	
	if (formID == "pagamento" || formID == "recebimento") {
		for (i = 0; i < x.length ;i++) {
			if (x.elements[i].getAttribute('type') == "text" ||
				x.elements[i].getAttribute('type') == "date" ||
				x.elements[i].getAttribute('type') == "number" ||
				x.elements[i].tagName == "SELECT" ||
				x.elements[i].getAttribute('id') == "recorrente") {
				x.elements[i].disabled = y;
			}
			
			if ((x.elements[i].id == "tipoRecorrencia" || x.elements[i].id == "recorrencia") && !document.getElementById("recorrente").checked ) {
				x.elements[i].disabled = true;
			}
		}
	}
	
}

function onloadCadastro() {
	var cpf = document.getElementById('cpf');
	
	if (cpf != null) {
		mascaraCpf(cpf);
	}
	
	var cnpj = document.getElementById('cnpj');
	
	if (cnpj != null) {
		mascaraCnpj(cnpj);
	}

	var telefone = document.getElementById('numtelefone');
	mascaraTelefone(telefone);

	var cep = document.getElementById('cep');
	mascaraCep(cep);
}

function mascaraTelefone(campoTexto) {
	if (campoTexto.value.length = 9) {
		campoTexto.value = campoTexto.value.replace(/(\d{5})(\d{4})/g,"\$1\-\$2");
	} 
	
	if (campoTexto.value.length = 8) {
		campoTexto.value = campoTexto.value.replace(/(\d{4})(\d{4})/g,"\$1\-\$2");
	} 
	
	if (campoTexto.value.length > 0 && campoTexto.value.length < 8) {
		campoTexto.value = campoTexto.value.padStart(9, "0").replace(/(\d{5})(\d{4})/g,"\$1\-\$2");
	} 
}

function completeDDD(campoTexto) {
	if (campoTexto.value.length > 0) {
		campoTexto.value = campoTexto.value.padStart(2, "0")
	}
}

function mascaraCep(campoTexto) {
	if (campoTexto.value.length > 0) {
		campoTexto.value = campoTexto.value.padStart(8, "0").replace(/(\d{5})(\d{3})/g,"\$1\-\$2");
	}	
}

function mascaraCnpj(campoTexto) {
	if (campoTexto.value.length > 0) {
		campoTexto.value = campoTexto.value.padStart(14, "0").replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g,"\$1.\$2.\$3\/\$4\-\$5");
	}
}

function mascaraCpf(campoTexto) {
	if (campoTexto.value.length > 0) {
		campoTexto.value = campoTexto.value.padStart(11, "0").replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g,"\$1.\$2.\$3\-\$4");
	}
}

function retirarFormatacao(campoTexto) {
    campoTexto.value = campoTexto.value.replace(/(\.|\/|\-)/g,"");
}

function confirmWindow(divID, inputID, newValue) {
	var elementDiv = document.getElementById(divID);
	var x = event.pageX - 90 + 'px';
	var y = ( event.pageY + 10 ) + 'px';
	
	
	elementDiv.style.border = '2px solid #9b5995';
	elementDiv.style.padding = '10px 10px 10px 10px';
	elementDiv.style.left = x;
	elementDiv.style.top = y;
	elementDiv.style.visibility = 'visible';
	elementDiv.style.background = 'white';
	
	var elementInput = document.getElementById(inputID);
	elementInput.value = newValue;

}

function confirmMatWindow(divID, materialID, newMaterialValue, servicoID, newServicoValue) {
	var elementDiv = document.getElementById(divID);
	var x = event.clientX + 'px';
	var y = ( event.clientY + 10 ) + 'px';
	
	
	elementDiv.style.border = '2px solid #9b5995';
	elementDiv.style.padding = '10px 10px 10px 10px';
	elementDiv.style.left = x;
	elementDiv.style.top = y;
	elementDiv.style.visibility = 'visible';
	elementDiv.style.background = 'white';

	var elementMaterial = document.getElementById(materialID);
	elementMaterial.value = newMaterialValue;
	
	var elementServico = document.getElementById(servicoID);
	elementServico.value = newServicoValue;

}