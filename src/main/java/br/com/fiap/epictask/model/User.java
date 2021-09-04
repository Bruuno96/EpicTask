package br.com.fiap.epictask.model;
 
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="TB_EPICTASK_USER")
@SequenceGenerator(allocationSize = 1, name = "user", sequenceName = "SQ_TB_EPICTASK_USER")
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
	@Column(name="id_user")
	private Long id;
	
	@NotBlank(message = "O nome do usuario é obrigatório")
	@Column(name="nm_name")
	private String username;

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
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	public List<Task> listaTasks;

}
