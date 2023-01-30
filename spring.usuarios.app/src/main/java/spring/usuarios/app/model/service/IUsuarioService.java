package spring.usuarios.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.usuarios.app.model.entity.Factura;
import spring.usuarios.app.model.entity.Pais;
import spring.usuarios.app.model.entity.Producto;
import spring.usuarios.app.model.entity.Region;
import spring.usuarios.app.model.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario>listaUsuarios();
	public Page<Usuario>findAll(Pageable pageable);
	public Usuario usuarioPorId(Long id);
	public Usuario guardar(Usuario usuario);
	public void eliminarPorId(Long id);
	
	public List<Region>findAllRegiones();
	public List<Pais>findAllPaises();
	
	public Factura findFacturaById(Long id);
	public Factura saveFactura(Factura factura);
	public void deleteFacturaById(Long id);
	
	public List<Producto>findProductoByNombre(String term);
}
