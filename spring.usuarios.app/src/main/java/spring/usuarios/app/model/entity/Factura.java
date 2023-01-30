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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = -3866845429097218244L;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	//PARA ASIGNAR LA FECHA DE LA FACTURA CUANDO ESTA SE CREA
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	
 	public Double getTotal() {
			Double total = 0.00;
			for(ItemFactura item : items) {
				total += item.getImporte();
			}
			return total;
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@JsonIgnoreProperties(value={"facturas", "hibernateLazyInitializer", "handler"}, allowSetters=true) 
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})		
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)		
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	
	
	
}
