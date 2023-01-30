package spring.usuarios.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.usuarios.app.model.dao.IFacturaDao;
import spring.usuarios.app.model.dao.IProductoDao;
import spring.usuarios.app.model.dao.IUsuarioDao;
import spring.usuarios.app.model.entity.Factura;
import spring.usuarios.app.model.entity.Pais;
import spring.usuarios.app.model.entity.Producto;
import spring.usuarios.app.model.entity.Region;
import spring.usuarios.app.model.entity.Usuario;


@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listaUsuarios() {
		// TODO Auto-generated method stub
		return usuarioDao.findAll();
	}
	
	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario usuarioPorId(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Usuario guardar(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioDao.save(usuario);
		return usuario;
	}

	@Override
	@Transactional
	public void eliminarPorId(Long id) {
		// TODO Auto-generated method stub
		usuarioDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		// TODO Auto-generated method stub
		return usuarioDao.findAllRegiones();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pais> findAllPaises() {
		// TODO Auto-generated method stub
		return usuarioDao.findAllPaises();
	}

	
	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		// TODO Auto-generated method stub
		return factDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		return factDao.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		// TODO Auto-generated method stub
		factDao.deleteById(id);
	}
	
	@Override
	public List<Producto> findProductoByNombre(String term) {
		// TODO Auto-generated method stub
		return productoDao.findByNombreContainingIgnoreCase(term);
	}

	
	@Autowired
	private IUsuarioDao usuarioDao;
	@Autowired
	private IFacturaDao factDao;
	
	@Autowired
	private IProductoDao productoDao;
	

	

	
	


	

}
