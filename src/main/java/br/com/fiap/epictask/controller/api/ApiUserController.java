package br.com.fiap.epictask.controller.api;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {
	
	@Autowired
	private UserRepository repo;
	
		
	@PostMapping
	public ResponseEntity<User> create(
			@RequestBody @Valid User user,  UriComponentsBuilder ub) throws Exception{
		
		repo.save(user);
		URI uri = ub.path("api/user/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		 return ResponseEntity.of(repo.findById(id));
	} 
	
	@DeleteMapping("{id}")
	public ResponseEntity<User> delete(@PathVariable Long id){
		Optional<User> user = repo.findById(id);
		if(user.isEmpty()) 
				return ResponseEntity.notFound().build();
		
		repo.deleteById(id);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("{id}")
	public ResponseEntity<User> update(@PathVariable @Valid Long id, @RequestBody User user){
		return repo.findById(id).map(x -> {
			x.setName(user.getUsername());
			x.setEmail(user.getEmail());
			x.setPassword(user.getPassword());
			x.setDataNascimento(user.getDataNascimento());
			User updatedUser= repo.save(x);
			return ResponseEntity.ok().body(updatedUser);
		}).orElse(ResponseEntity.notFound().build());
		
	}
	
	
	
}
