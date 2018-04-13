package com.agonzales.SistemaEscolar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

	@GetMapping("/login")
    public String login() {
        return "login";
    }	
    
    @GetMapping("/403")
    public String denied() {
        return "403";
    }	
    
    @GetMapping("/login/failure")
 	public String loginFailure(Model model) {
		return "redirect:/login";
	}

    @GetMapping("/error")
 	public String error(Model model) {
		return "404";
	}

}
