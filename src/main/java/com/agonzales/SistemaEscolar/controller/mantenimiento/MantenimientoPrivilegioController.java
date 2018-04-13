package com.agonzales.SistemaEscolar.controller.mantenimiento;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agonzales.SistemaEscolar.domain.Privilegio;
import com.agonzales.SistemaEscolar.service.PrivilegioService;


@Controller
@RequestMapping("/mantenimiento/privilegio")
public class MantenimientoPrivilegioController {
	
	@Autowired
	private PrivilegioService privilegioService;

	@RequestMapping(value="/listar")
	public String privilegioListar(HttpSession session, Model model){
		return "mantenimiento/privilegio/treePrivilegio";
	}
	
	@RequestMapping(value="/listaPrivilegiosPadre", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> listaPrivilegiosPadre(Integer idRol){
		List<Map<String, Object>> listaDeArbolDePrivilegios = privilegioService.obtenerListaDePrivilegiosPadres(idRol);
		return listaDeArbolDePrivilegios;
	}

	@RequestMapping(value="/ver")
	public String privilegioVer(HttpSession session, Model model, Integer id){
		if(id != null){
			Privilegio privilegio = privilegioService.get(id);
			model.addAttribute("privilegio", privilegio);
			model.addAttribute("nombreMostrar", privilegio.getNombre());
		}
		return "mantenimiento/privilegio/verPrivilegio";
	}

}
