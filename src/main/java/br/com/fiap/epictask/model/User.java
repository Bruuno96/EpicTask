package br.com.fiap.epictask.model;
 
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TB_EPICTASK_USER")
@SequenceGenerator(allocationSize = 1, name = "user", sequenceName = "SQ_TB_EPICTASK_USER")
public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
	@Column(name="id_user")
	private Long id;
	
	@NotBlank(message = "O nome do usuario é obrigatório")
	@Column(name="nm_name")
	private String name;

	@Column(name="ds_password")
	@Length(min = 8, message = "A Senha deve ter no minimo 8 caracteres")
	private String password;
	
	@NotBlank(message = "Email é obrigatório")
	@Column(name="ds_email", unique = true)
	private String email;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_nascimento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@NotBlank(message = "O usuário do GitHub é obrigatório")
	@Column(name="ft_perfil")
	private String githubuser;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<Task> listaTasks;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Role> roles;
	
	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	
	public String getAvatarUrl() {
		return "https://avatars.githubusercontent.com/" + this.githubuser;	
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
