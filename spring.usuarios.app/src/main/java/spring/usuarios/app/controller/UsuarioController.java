package spring.usuarios.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.usuarios.app.model.entity.Pais;
import spring.usuarios.app.model.entity.Region;
import spring.usuarios.app.model.entity.Usuario;
import spring.usuarios.app.model.service.IUploadService;
import spring.usuarios.app.model.service.IUsuarioService;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	
	@GetMapping("/user")
	public List<Usuario>listarUsuarios(){
		return usuarioService.listaUsuarios();
	}
	
	@GetMapping("/user/page/{page}")
	public Page<Usuario>index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 5);
		return usuarioService.findAll(pageable);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> usuario(@PathVariable Long id) {
		Map<String,Object>response = new HashMap<>();
		Usuario usuario =  null;
		try{
			usuario = usuarioService.usuarioPorId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		if(usuario==null) {
			response.put("mensaje", "El usuario con Id '" + id.toString() + "' no existe en la Base de datos.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Map<String,Object>response = new HashMap<>();
		Usuario newUser = null;
		
		if(result.hasErrors()) {
			List<String>errors = result.getFieldErrors()
					.stream().map(err -> {
						return "El campo " + err.getField() + " " + err.getDefaultMessage();
						})
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			newUser = usuarioService.guardar(usuario);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("newUser",newUser);
		response.put("success", "Usuario creado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?>actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result){
		Map<String,Object>response = new HashMap<>();
		Usuario userbd = usuarioService.usuarioPorId(id);
		Usuario userUpdated = null;
		
		if(result.hasErrors()) {
			List<String>errors = result.getFieldErrors()
					.stream().map(err -> {
						return "El campo " + err.getField() + " " + err.getDefaultMessage();
						})
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(userbd == null) {
			response.put("mensaje", "No se pudo editar, el cliente con Id '".concat(id.toString().concat("' no existe en la base de datos.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);	
		}
		
		try {
			userbd.setNombre(usuario.getNombre());
			userbd.setApellido(usuario.getApellido());
			userbd.setEmail(usuario.getEmail());
			userbd.setEdad(usuario.getEdad());
			userbd.setNumber(usuario.getNumber());
			userbd.setPais(usuario.getPais());
			userbd.setRegion(usuario.getRegion());
			userbd.setCreateAt(usuario.getCreateAt());
			userUpdated = usuarioService.guardar(userbd);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos.");
			response.put("error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("userUpdated", userUpdated);
		response.put("success", "Usuario actualizado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?>eliminarPorId(@PathVariable Long id){
		Map<String,Object>response = new HashMap<>();
		Usuario userbd = usuarioService.usuarioPorId(id);
		
		if(userbd == null) {
			response.put("mensaje", "No se pudo eliminar, el cliente con Id '".concat(id.toString().concat("' no existe en la base de datos.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);	
		}
		
		try {
			String fotoUser = userbd.getFoto();
			uploadService.eliminar(fotoUser);
			usuarioService.eliminarPorId(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos.");
			response.put("error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Usuario eliminado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
	}
	
	
	@GetMapping("/user/regiones")
	public List<Region>listarRegiones(){
		return usuarioService.findAllRegiones();
	}
		
	
	@GetMapping("/user/paises")
	public List<Pais>listarPaises(){
		return usuarioService.findAllPaises();
	}
	
	@PostMapping("/user/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id")Long id){
		Map<String,Object>response = new HashMap<>();
		Usuario usuario = usuarioService.usuarioPorId(id);
		if(!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al cargar la imagen.");
				response.put("error", e.getMessage().concat(" : ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			
			String nombreFotoAnterior = usuario.getFoto();
			
			uploadService.eliminar(nombreFotoAnterior);
			
			usuario.setFoto(nombreArchivo);
			usuarioService.guardar(usuario);
			response.put("usuario", usuario);
			response.put("mensaje", "Imagen cargada correctamente: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")												
	public ResponseEntity<Resource>verFoto(@PathVariable String nombreFoto){
		Resource recurso  = null;
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SE PASAN LAS CABECERAS PARA FORZAR A QUE SE PUEDA DESCARGAR LA IMAGEN. EL ATRIBUTO ATTACHMENT ES EL QUE FUERZA A LA IMAGEN.
		HttpHeaders cabecera  = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	
	@Autowired
	private IUsuarioService usuarioService; 
	@Autowired
	private IUploadService uploadService;
}
