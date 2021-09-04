package br.com.fiap.epictask.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import br.com.fiap.epictask.service.UserService;
import br.com.fiap.epictask.util.Util;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService serviceUser;
	
	@RequestMapping("/cadastro")
	public String registerPage(User user) {
		return "register";
	}
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	
	@GetMapping("/listaUsuarios")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("listaUsuarios");
		List<User> users = repository.findAll(); 
		modelAndView.addObject("users", users);
		return modelAndView;
	}
	
	
	@PostMapping("/cadastro")
	public String save(@Valid @ModelAttribute User user, BindingResult result) throws Exception {
		if(result.hasErrors()) return "register";
		
		// service cadastra o usuario com senha criptografa com MD5 pacote UTIL
		serviceUser.salvarUsuario(user);
		return "redirect:/listaUsuarios";
	}
	
	@PostMapping("/login")
	public String login(@Valid User user, BindingResult result, HttpSession session ) throws NoSuchAlgorithmException{
		ModelAndView model = new ModelAndView();
		model.addObject("user", new User());
		if(result.hasErrors()) {
			model.setViewName("/login");
			return "redirect:/login";
		} 
		
		User userLogin = serviceUser.login(user.getEmail(), Util.md5(user.getPassword()));
		if(userLogin == null ) {
			model.addObject("msg","Usuario nao cadastrado");
			
		}else {
			session.setAttribute("user", user);
			return "/task";
		}
		return null;
	}	

}
