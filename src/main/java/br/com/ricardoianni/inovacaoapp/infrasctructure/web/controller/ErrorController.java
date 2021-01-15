package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/error")
public class ErrorController {
	
	@GetMapping(path = "/")
	public String erro(Model model) {
		return "menu";
	}
	
}