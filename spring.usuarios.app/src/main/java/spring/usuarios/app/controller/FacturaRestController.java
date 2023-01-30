package spring.usuarios.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.usuarios.app.model.entity.Factura;
import spring.usuarios.app.model.entity.Producto;
import spring.usuarios.app.model.service.IUsuarioService;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class FacturaRestController {

	
	@GetMapping("/invoice/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Factura facturaPorId(@PathVariable Long id) {
		return usuarioService.findFacturaById(id);
	}
	

	@DeleteMapping("/invoice/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		usuarioService.deleteFacturaById(id);
	}
	
	
	@GetMapping("/invoice/filter-products/{term}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Producto>filtrarProductos(@PathVariable String term){
		return usuarioService.findProductoByNombre(term);
	}
	
	

	@PostMapping("/invoice")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Factura crear(@RequestBody Factura factura) {
		 return usuarioService.saveFactura(factura);
	}
	
	@Autowired
	private IUsuarioService usuarioService;
	
}
