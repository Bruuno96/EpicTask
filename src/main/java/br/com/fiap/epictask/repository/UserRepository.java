package br.com.fiap.epictask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.epictask.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("select u from User u where u.email = :email")
	public Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.email = :email and u.password = :password")
	public User SearchLogin(String email, String password);

}
