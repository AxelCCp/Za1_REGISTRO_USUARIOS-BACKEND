package spring.usuarios.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import spring.usuarios.app.model.entity.Pais;
import spring.usuarios.app.model.entity.Region;
import spring.usuarios.app.model.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
	
	@Query("from Region")
	public List<Region>findAllRegiones();
	
	@Query("from Pais")
	public List<Pais>findAllPaises();

}
