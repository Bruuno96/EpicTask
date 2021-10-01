package br.com.fiap.epictask.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;

@Repository
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(username);
		if (user == null ) throw new UsernameNotFoundException("e-mail not found");
		return user.get();
	}
	
	public static PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
}

}