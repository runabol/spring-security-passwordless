package com.example.passwordless;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index (Authentication aAuthentication, Model aModel) {
	  aModel.addAttribute("auth", aAuthentication);
	  return "index";
	} 
	
}
