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

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.TaskRepository;

@Controller
public class TaskController {
	
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
	public String save(@Valid Task task, BindingResult result) {
		if(result.hasErrors()) return "cadastrar-task";
		repository.save(task);
		return "lista-tasks";
	}
	
	@GetMapping("release/{id}")
	public Task hold(@PathVariable Long id, Authentication auth) {
		Optional<Task> task = repository.findById(id);
		
		if (task.isEmpty()) {
			System.out.println("tarefa nao encontrada");
		}
		
		Task tasknew = task.get();
		User user = (User) auth.getPrincipal();
		tasknew.setUser(user);
		return repository.save(tasknew);
		
	}
	
//	@GetMapping("release/{id}")
//	public String release(@PathVariable Long id, Authentication auth) {
//		Optional<Task> task = repository.findById(id);
//		
//		if (task.isEmpty()) {
//			System.out.println("tarefa nao encontrada");
//		}
//		
//		Task tasknew = task.get();
//		
//		if(tasknew.getUser().equals(auth.getPrincipal())) {
//			throw new Exception()
//		}
//		User user = (User) auth.getPrincipal();
//		tasknew.setUser(user);
//		return;
//		
//	}
	
	
}
