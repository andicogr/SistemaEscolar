package com.agonzales.SistemaEscolar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agonzales.SistemaEscolar.domain.Privilegio;
import com.agonzales.SistemaEscolar.repository.PrivilegioRepository;
import com.agonzales.SistemaEscolar.repository.RolPrivilegioRespository;


@Service
public class PrivilegioService {

	@Autowired
	private PrivilegioRepository privilegioRespository;
	
	@Autowired
	private RolPrivilegioRespository rolPrivilegioRepository;

	public List<Map<String, Object>> obtenerListaDePrivilegiosPadres(Integer idRol){
		List<Map<String, Object>> listaDeArbolDePrivilegios = new ArrayList<Map<String,Object>>();
		List<Privilegio> listaPrivilegiosPadres = privilegioRespository.findPrivilegiosPadreActivos();

		for(Privilegio privilegio : listaPrivilegiosPadres){
			Map<String, Object> privilegioMap = new HashMap<String, Object>();
			List<Map<String, Object>> childrens = obtenerHijosDePrivilegio(privilegio, idRol);
			privilegioMap.put("id", privilegio.getId());
			privilegioMap.put("text", privilegio.getDescripcion());
			privilegioMap.put("checked", isPrivilegioChecked(privilegio, idRol, childrens));
			privilegioMap.put("children", childrens);
			listaDeArbolDePrivilegios.add(privilegioMap);
		}

		return listaDeArbolDePrivilegios;
	}

	private List<Map<String, Object>> obtenerHijosDePrivilegio(Privilegio privilegio, Integer idRol){
		List<Map<String, Object>> listaDePrivilegiosHijo = new ArrayList<Map<String,Object>>();

		for(Privilegio privilegioHijo : privilegio.getPrivilegios()){
			List<Map<String, Object>> childrens = obtenerHijosDePrivilegio(privilegioHijo, idRol);
			Map<String, Object> privilegioHijoMap = new HashMap<String, Object>();
			privilegioHijoMap.put("id", privilegioHijo.getId());
			privilegioHijoMap.put("text", privilegioHijo.getDescripcion());
			privilegioHijoMap.put("checked", isPrivilegioChecked(privilegioHijo, idRol, childrens));
			privilegioHijoMap.put("children", childrens);
			listaDePrivilegiosHijo.add(privilegioHijoMap);
		}
		return listaDePrivilegiosHijo;
	}

	private boolean isPrivilegioChecked(Privilegio privilegio, Integer idRol, List<Map<String, Object>> hijos){
		boolean isPrivilegioAsociadoRol = rolPrivilegioRepository.exists(privilegio.getId(), idRol);
		if(!hijos.isEmpty()){
			boolean isTodosHijosChecked = true;
			for(Map<String, Object> privilegioMap : hijos){
				if(((Boolean) privilegioMap.get("checked")) == false){
					isTodosHijosChecked = false;
				}
			}
			return isPrivilegioAsociadoRol && isTodosHijosChecked;
		}else{
			return isPrivilegioAsociadoRol;
		}
	}

	public Privilegio get(Integer id){
		Optional<Privilegio> privilegio = privilegioRespository.findById(id);
		return privilegio.get();
	}

}
