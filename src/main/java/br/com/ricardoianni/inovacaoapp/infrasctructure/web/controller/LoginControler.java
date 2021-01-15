package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControler {

	@GetMapping(path = {"/login", "/"})
	public String login(Model model) {
		model.addAttribute("loginError", false);
		return "login";
	}
	
	@GetMapping(path = "/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
}
