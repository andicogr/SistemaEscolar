package com.agonzales.SistemaEscolar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.Usuario;
import com.agonzales.SistemaEscolar.service.UsuarioRolService;
import com.agonzales.SistemaEscolar.service.UsuarioService;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.Util;
import com.agonzales.SistemaEscolar.util.VariablesSession;

@Controller
@RequestMapping("/configuracionUsuario")
public class ConfiguracionUsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@GetMapping(value = "/verCambiarRol")
	public String verCambiarRol(HttpSession session, Model model) {
		Integer idUsuarioSession = (Integer) session.getAttribute(Constantes.USUARIO_SESSION_ID);
		Usuario usuarioSession = usuarioService.get(idUsuarioSession);
		List<Rol> listaRolesActivos = usuarioRolService.findActiveRoleByUserId(idUsuarioSession);
		model.addAttribute("listaRolesActivos", listaRolesActivos);
		model.addAttribute("usuarioSession", usuarioSession);
		model.addAttribute("rolActualId", VariablesSession.getAttribute(Constantes.ROL_SESSION_ID));
		return "configuracionUsuario/cambiarRol";
	}

	@GetMapping(value = "/verCambiarPassword")
	public String verCambiarPassword(HttpSession session, Model model) {
		return "configuracionUsuario/cambiarPassword";
	}
	
	@PostMapping(value="/cambiarPassword")
	@ResponseBody
	public Map<String, Object> cambiarPassword(String passwordActual, String nuevoPassword, 
			String confirmarPassword){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		Usuario usuarioSession = usuarioService.getUserSession();
		if(!usuarioService.comparePassword(passwordActual, usuarioSession.getPassword())) {
			notificacion = Util.crearNotificacionError("Error", "La clave actual ingresada no es la correcta.");
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}
		
		if(!nuevoPassword.equals(confirmarPassword)) {
			notificacion = Util.crearNotificacionError("Error", "Las claves ingresadas no son iguales.");
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}
		
		usuarioSession.setPassword(nuevoPassword);
		retorno = usuarioService.update(usuarioSession, true);

		return retorno;
	}


}
