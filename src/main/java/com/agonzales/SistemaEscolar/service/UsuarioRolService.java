package com.agonzales.SistemaEscolar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.UsuarioRol;
import com.agonzales.SistemaEscolar.repository.UsuarioRolRepository;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.SqlExceptionMessage;
import com.agonzales.SistemaEscolar.util.Util;

@Service
public class UsuarioRolService {

	@Autowired
	private UsuarioRolRepository usuarioRolRepository;
	
	public UsuarioRol get(Integer id) {
		Optional<UsuarioRol> usuarioRol = usuarioRolRepository.findById(id);
		return usuarioRol.get();
	}

	public Map<String, Object> save(UsuarioRol usuarioRol){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		try {
			usuarioRol = this.saveUsuarioRol(usuarioRol);
			notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_REGISTRO_CORRECTO);
		} catch (DataAccessException dataAccessException) {
			String titleError = "Error al intentar registrar el Rol " + usuarioRol.getNombreRol() + 
					" al Usuario " + usuarioRol.getUsuario().getUsername();
			notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
		}

		retorno.put("notificacion", notificacion);
		retorno.put("id", usuarioRol.getId());
		retorno.put("idUsuario", usuarioRol.getUsuarioId());
		retorno.put("estado", true);

		return retorno;
	}
	

	public Map<String, Object> update(UsuarioRol usuarioRol){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		if(usuarioRol.getId() != null){
			UsuarioRol actual = this.get(usuarioRol.getId());
			actual.setRol(usuarioRol.getRol());
			actual.setActivo(usuarioRol.isActivo());

			try {
				actual = this.saveUsuarioRol(actual);
				notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_ACTUALIZACION_CORRECTA);
			} catch (DataAccessException dataAccessException) {
				String titleError = "Error al intentar actualizar el Rol " + usuarioRol.getNombreRol() + 
						" del Usuario " + usuarioRol.getUsuario().getUsername();
				notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
			}
		}else{
			notificacion = Util.notificacionErrorDelSistema();
		}

		retorno.put("notificacion", notificacion);
		retorno.put("id", usuarioRol.getId());
		retorno.put("idUsuario", usuarioRol.getUsuarioId());
		retorno.put("estado", true);

		return retorno;
	}
	
	public Map<String, Object> delete(Integer[] ids){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		boolean estadoEliminacion = true;
		String textoNotificacion = Constantes.MENSAJE_REGISTRO_ELIMINADO;
		if(ids.length > 1){
			textoNotificacion = Constantes.MENSAJE_REGISTROS_ELIMINADOS;
		}
		notificacion = Util.crearNotificacionInfo("Informacion", textoNotificacion);
		for(Integer id : ids){
			UsuarioRol usuarioRol = this.get(id);

			try {
				this.deleteUsuarioRol(usuarioRol);
			} catch (DataAccessException dataAccessException) {
				String titleError = "Error al intentar eliminar el Rol " + usuarioRol.getNombreRol() + 
						" del Usuario " + usuarioRol.getUsuario().getUsername();
				notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
				estadoEliminacion = false;
			}
		}

		retorno.put("notificacion", notificacion);
		retorno.put("estado", estadoEliminacion);
		return retorno;
	}
	
	public Map<String, Object> delete(List<UsuarioRol> usuarioRoles){
		Integer[] ids = new Integer[usuarioRoles.size()];
		int i = 0;
		for(UsuarioRol usuarioRol: usuarioRoles) {
			ids[i] = usuarioRol.getId();
			i ++;
		}
		return this.delete(ids);
	}
	
	public List<Rol> findActiveRoleByUserId(Integer userId){
		return usuarioRolRepository.findActiveRoleByUserId(userId);
	}
	
	@Transactional
	private UsuarioRol saveUsuarioRol(UsuarioRol usuarioRol) {
		return usuarioRolRepository.save(usuarioRol);
	}
	
	@Transactional
	private void deleteUsuarioRol(UsuarioRol usuarioRol) {
		usuarioRolRepository.delete(usuarioRol);
	}

}
