package com.agonzales.SistemaEscolar.controller.mantenimiento;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.UsuarioRol;
import com.agonzales.SistemaEscolar.service.RolService;
import com.agonzales.SistemaEscolar.service.UsuarioRolService;
import com.agonzales.SistemaEscolar.util.Constantes;

@Controller
@RequestMapping("/mantenimiento/usuariorol")
public class MantenimientoUsuarioRolController {

	@Autowired
	private UsuarioRolService usuarioRolService;

	@Autowired
	private RolService rolService;

	@RequestMapping(value="/ver")
	public String ver(Model model, Integer id, Integer idUsuario){
		model.addAttribute("idUsuario", idUsuario);
		List<Rol> listaRolesActivos = rolService.listUnassignedRoleByUserId(idUsuario);
		if(id != null){
			UsuarioRol usuarioRol = usuarioRolService.get(id);
			model.addAttribute("usuarioRol", usuarioRol);
			model.addAttribute("nombreMostrar", usuarioRol.getNombreRol());
			listaRolesActivos.add(usuarioRol.getRol());
		}

		model.addAttribute("listaRolesActivos", listaRolesActivos);

		return "mantenimiento/usuariorol/verUsuarioRol";
	}

	@RequestMapping(value="/guardar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> guardar(Model model, UsuarioRol usuarioRol, String estado){
		usuarioRol.setActivo(estado.equals(Constantes.STRING_TRUE));
		Map<String, Object> retorno = usuarioRolService.save(usuarioRol);
		return retorno;
	}

	@RequestMapping(value="/actualizar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> actualizar(Model model, UsuarioRol usuarioRol, String estado){
		usuarioRol.setActivo(estado.equals(Constantes.STRING_TRUE));
		Map<String, Object> retorno = usuarioRolService.update(usuarioRol);
		return retorno;
	}

	@RequestMapping(value="/eliminar")
	@ResponseBody
	public Map<String, Object> eliminar(Model model, Integer[] ids){
		Map<String, Object> retorno = usuarioRolService.delete(ids);
		return retorno;
	}

}
