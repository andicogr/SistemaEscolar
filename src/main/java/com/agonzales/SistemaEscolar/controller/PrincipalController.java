package com.agonzales.SistemaEscolar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agonzales.SistemaEscolar.config.CustomUserDetailsService;
import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.Usuario;
import com.agonzales.SistemaEscolar.service.AlertService;
import com.agonzales.SistemaEscolar.service.RolService;
import com.agonzales.SistemaEscolar.service.UsuarioService;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.VariablesSession;

@Controller
public class PrincipalController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AlertService alertService;

    @RequestMapping("/")
 	public String principal(Model model, Integer idRol, boolean porDefecto) {
    	Usuario usuario = usuarioService.getUserSession();
		VariablesSession.setAttribute(Constantes.USUARIO_SESSION_ID, usuario.getId());
		VariablesSession.setAttribute(Constantes.USUARIO_SESION, usuario.getUsername());

		if(idRol != null){
			Rol rol = rolService.get(idRol);
			if(porDefecto) {
				usuarioService.updateRolPorDefecto(usuario, rol);
			}
			customUserDetailsService.aupdateAuthorities(rol);
		}else{
			idRol = usuario.getRolPorDefecto().getId();
		}

		VariablesSession.setAttribute(Constantes.ROL_SESSION_ID, idRol);
		
		List<Map<String, Object>> listAlerts = alertService.findFirstFourAlertsNotRead(usuario.getId());
		int cantidadAlertas =  alertService.countByToUsuarioIdAndRead(usuario.getId());

		model.addAttribute("alerts", listAlerts);
		model.addAttribute("cantidadAlertas", cantidadAlertas);

		return "principal";
	}
}
