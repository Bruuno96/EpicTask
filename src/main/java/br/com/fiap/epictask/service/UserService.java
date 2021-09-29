package br.com.fiap.epictask.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.exceptions.CriptoExistException;
import br.com.fiap.epictask.exceptions.EmailExistExeption;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import br.com.fiap.epictask.util.Util;

@Service
public class UserService {
	
		@Autowired
		private UserRepository repo;
	
		public void salvarUsuario(User user) throws Exception{
			
			try {
				if (repo.findByEmail(user.getEmail()) == null) {
					throw new EmailExistExeption("Ja existe um email cadastrado "+ user.getEmail());
				}
				
				user.setPassword(AuthenticationService.getPasswordEncoder().encode(user.getPassword()));
			} catch (Exception e) {
				throw new CriptoExistException("Erro na criptografia da senha");
			}
			
			repo.save(user);
		}
		
		public User login(String email, String password) {
			User loginUser = repo.SearchLogin(email, password);
			return loginUser;
		}

}
