package spring.usuarios.app.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

	private static final long serialVersionUID = -4910705658298696698L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
		
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="no puede estar vacío.")
	@Size(min=2,max=12, message="El tamaño debe ser entre 2 y 12 caracteres.")
	@Column(nullable=false)
	private String nombre;
	
	@NotEmpty(message="no puede estar vacío.")
	private String apellido;
	
	@NotEmpty(message="no puede estar vacío.")
	@Email(message="no está bien formado.")
	@Column(nullable=false, unique=true)
	private String email;
	
	@NotEmpty(message="no puede estar vacío.")
	@Column(nullable=false, unique=true)
	private String number;
	
	@Min(value = 5, message = "no debe ser menor a 5 años.")
    @Max(value = 300, message = "no debe ser mayor a 300 años.")
	@NotNull(message="no puede estar vacío.")
	private Integer edad;
	
	@NotNull(message="no puede ser vacío")
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="region_id") 						
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) 
	private Region region;
	
	@NotNull(message="no puede ser vacío.")
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="pais_id") 						
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) 
	private Pais pais;
	
	@NotNull(message="no puede quedar vacío.")
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	private String foto;
	
	@JsonIgnoreProperties(value = {"usuario","hibernateLazyInitializer","handler"},  allowSetters = true) 
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL) 
	private List<Factura>facturas;


	
	
}
