package com.agonzales.SistemaEscolar.controller.mantenimiento;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.service.RolService;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.PaginacionDTO;

@Controller
@RequestMapping("/mantenimiento/rol")
public class MantenimientoRolController {
	
	@Autowired
	private RolService rolService;
	
	@RequestMapping(value="/listar")
	public String listar(HttpSession session, Model model){
		return "mantenimiento/rol/listarRoles";
	}

	@RequestMapping(value="/listaJson")
	@ResponseBody
	public  Map<String, Object> listaJson(HttpSession session, PaginacionDTO paginacion,
			String nombre, String estado){
		Map<String, Object> datos = rolService.getDataTableJson(paginacion, nombre, estado);
		return datos;
	}
	
	@RequestMapping(value="/ver")
	public String ver(HttpSession session, Model model, Integer id, String tab){
		if(id != null){
			Rol rol = rolService.get(id);
			model.addAttribute("rol", rol);
			model.addAttribute("nombreMostrar", rol.getNombre());
		}
		model.addAttribute("tab", tab == null ? "privilegios" : tab);
		return "mantenimiento/rol/verRol";
	}
	
	@RequestMapping(value="/guardar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> guardar(Model model, Rol rol, String estado){
		rol.setActivo(estado.equals(Constantes.STRING_TRUE));
		Map<String, Object> retorno = rolService.save(rol);
		return retorno;
	}
	
	@RequestMapping(value="/actualizar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> actualizar(Model model, Rol rol, String estado){
		rol.setActivo(estado.equals(Constantes.STRING_TRUE));
		Map<String, Object> retorno = rolService.update(rol);
		return retorno;
	}

	@RequestMapping(value="/eliminar")
	@ResponseBody
	public Map<String, Object> eliminar(Model model, Integer[] ids){
		Map<String, Object> retorno = rolService.delete(ids);
		return retorno;
	}
	
	@RequestMapping(value="/actualizarPrivilegios")
	@ResponseBody
	public Map<String, Object> actualizarPrivilegios(Integer idRol, Integer[] idPrvivilegios){
		return rolService.actualizarPrivilegios(idRol, idPrvivilegios);
	}

}
