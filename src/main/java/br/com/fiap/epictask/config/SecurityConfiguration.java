package br.com.fiap.epictask.config;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.fiap.epictask.service.AuthenticationService;

@Configuration
@EnableWebSecurity 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;

	// AUTENTICAÇÃO
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	// AUTORIZAÇÃO
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/lista-tasks/**")
			.hasRole("ADMIN")
		.antMatchers("/lista-task", "/lista-usuarios")		
			.authenticated()
		.and()
//			.formLogin(form -> form
//					.loginPage("/login")
//					.usernameParameter("email")
//					.passwordParameter("password")
//					.defaultSuccessUrl("/lista-tasks")
//					)
		.formLogin()
		.loginPage("/login").and()

			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login");	
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
		 
}
