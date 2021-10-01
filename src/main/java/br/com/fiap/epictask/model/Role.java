package br.com.fiap.epictask.model;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Data
public class Role implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private  String name;
	
	@Override
	public String getAuthority() {
		return name;
	}

}
