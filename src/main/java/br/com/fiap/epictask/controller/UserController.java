package br.com.fiap.epictask.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import br.com.fiap.epictask.service.AuthenticationService;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@RequestMapping("/cadastro")
	public String registerPage(User user) {
		return "register";
	}
	
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	
	@GetMapping("/lista-usuarios")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("lista-usuarios");
		Iterable<User> users = repository.findAll(); 
		modelAndView.addObject("users", users);
		return modelAndView;
	}
	
	
	@PostMapping("/cadastro")
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect, Authentication auth) {
		if(result.hasErrors()) return "register";
		user.setPassword(AuthenticationService.getPasswordEncoder().encode( user.getPassword()));
		repository.save(user);
		return "redirect:/login";
	}
	

}
