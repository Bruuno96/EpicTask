package br.com.fiap.epictask.controller;

import java.util.List; 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.epictask.exception.TaskNotFoundException;
import br.com.fiap.epictask.exceptions.NotAllowedException;
import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.TaskRepository;

@Controller
public class TaskController {
	
	User user;
	
	@Autowired
	private TaskRepository repository;

	@GetMapping("/lista-tasks")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("lista-tasks");
		List<Task> tasks = repository.findAll();
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
		
	}
	
	@RequestMapping("/lista-tasks/new")
	public String create(Task task) {
		return "cadastrar-task";
	}
	
	@PostMapping("/lista-tasks")
	public String save(@Valid Task task, BindingResult result, Authentication auth) {
		if(result.hasErrors()) return "redirct:/task-form";
		User user = (User) auth.getPrincipal();
		task.setUser(user);
		repository.save(task);
		return "redirect:/lista-tasks";
	}
	
	@GetMapping("/hold/{id}")
	public String hold(@PathVariable Long id, Authentication auth) throws NotAllowedException, TaskNotFoundException {
		Optional<Task> optional = repository.findById(id);
		
		if(optional.isEmpty())
			throw new TaskNotFoundException("Tarefa não encontrada");
		
		Task task = optional.get();
		if(task.getUser() != null)
			throw new NotAllowedException("Tarefa já está atribuída");

		User user = (User) auth.getPrincipal();
		task.setUser(user);
		repository.save(task);
			
		return "redirect:/task";
	}
	
	@GetMapping("/release/{id}")
	public String release(@PathVariable Long id, Authentication auth) throws TaskNotFoundException, NotAllowedException {
		Optional<Task> optional = repository.findById(id);
		
		if(optional.isEmpty())
			throw new TaskNotFoundException("Tarefa não encontrada");
		
		Task task = optional.get();

		if(!task.getUser().equals(auth.getPrincipal()))
			throw new NotAllowedException("Tarefa está atribuída para outra pessoa");
		
		task.setUser(null);
		repository.save(task);
			
		return "redirect:/task";
	}
	
	
}
