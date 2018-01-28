package com.example.passwordless;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SigninController {

  @GetMapping("/signin")
  public String login () {
    return "signin";
  }
  
  @PostMapping("/signin")
  public String login (@RequestParam("email") String aEmail) {
    return "";
  }
	
}
