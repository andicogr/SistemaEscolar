package com.agonzales.SistemaEscolar.controller.mantenimiento;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agonzales.SistemaEscolar.config.aspectj.Loggable;
import com.agonzales.SistemaEscolar.domain.Usuario;
import com.agonzales.SistemaEscolar.service.UsuarioService;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.PaginacionDTO;
import com.agonzales.SistemaEscolar.util.Util;

@Controller
@RequestMapping("/mantenimiento/usuario")
public class MantenimientoUsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Loggable
	@RequestMapping(value="/listar")
	public String listar(Model model){
		return "mantenimiento/usuario/listarUsuarios";
	}

	@Loggable
	@RequestMapping(value="/listaJson")
	@ResponseBody
	public  Map<String, Object> listaJson(PaginacionDTO paginacion, String username, String estado){
		Map<String, Object> datos = usuarioService.getDataTableJson(paginacion, username, estado);
		return datos;
	}

	@GetMapping(value="/exists/username")
	@ResponseBody
	public boolean isUsernameInUse(String username,@RequestParam(defaultValue = "0", required = false) Integer id){
		return !usuarioService.existsByUsernameAndIdNot(username, id);
	}
	
	@Loggable
	@RequestMapping(value="/ver")
	public String ver(Model model, Integer id){
		if(id != null){
			Usuario usuario = usuarioService.get(id);

			usuario.setPassword("$2a$10$aEw");
			model.addAttribute("usuario", usuario);
			model.addAttribute("nombreMostrar", usuario.getUsername());
		}
		return "mantenimiento/usuario/verUsuario";
	}
	
	@Loggable
	@RequestMapping(value="/guardar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> guardar(Model model, Usuario usuario, String estado,  
			String bloqueado, String expirarUsuario, String fechaExpiracionUsuario){

		usuario.setFechaExpiracionUsuario(Util.formatDateHTML(fechaExpiracionUsuario));
		usuario.setActivo(estado.equals(Constantes.STRING_TRUE));
		usuario.setBloqueado(bloqueado.equals(Constantes.STRING_TRUE));
		usuario.setExpirarUsuario(expirarUsuario.equals(Constantes.STRING_TRUE));

		Map<String, Object> retorno = usuarioService.save(usuario);
		return retorno;
	}

	@Loggable
	@RequestMapping(value="/actualizar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> actualizar(Model model, Usuario usuario, boolean updatePassword, 
			String estado, String bloqueado, String expirarUsuario, String fechaExpiracionUsuario){

		usuario.setFechaExpiracionUsuario(Util.formatDateHTML(fechaExpiracionUsuario));
		usuario.setActivo(estado.equals(Constantes.STRING_TRUE));
		usuario.setBloqueado(bloqueado.equals(Constantes.STRING_TRUE));
		usuario.setExpirarUsuario(expirarUsuario.equals(Constantes.STRING_TRUE));
		Map<String, Object> retorno = usuarioService.update(usuario, updatePassword);
		return retorno;
	}

	@Loggable
	@RequestMapping(value="/eliminar")
	@ResponseBody
	public Map<String, Object> eliminar(Model model, Integer[] ids){
		Map<String, Object> retorno = usuarioService.delete(ids);
		return retorno;
	}
	
	@RequestMapping(value="/desbloquearUsuario")
	@ResponseBody
	public Map<String, Object> desbloquearUsuario(Integer[] ids){
		return usuarioService.desbloquearUsuarios(ids);
	}
}
