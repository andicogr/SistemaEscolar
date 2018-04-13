package com.agonzales.SistemaEscolar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agonzales.SistemaEscolar.domain.QUsuario;
import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.Usuario;
import com.agonzales.SistemaEscolar.repository.UsuarioRepository;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.PaginacionDTO;
import com.agonzales.SistemaEscolar.util.SqlExceptionMessage;
import com.agonzales.SistemaEscolar.util.Util;
import com.querydsl.core.BooleanBuilder;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario getUserSession(){
		return this.get(getUserIdSession());
	}

	public Integer getUserIdSession(){
		User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioRepository.getUID(usuario.getUsername());
	}

	public Usuario get(Integer id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.get();
	}

	public Map<String, Object> getDataTableJson(PaginacionDTO paginacion, 
			String username, String estado){

		if(paginacion.getiDisplayLength()==null){
			return null;
		}

		BooleanBuilder queryWhere = generateWhereBuilder(username, estado);

		paginacion.setCountResult((int) usuarioRepository.count(queryWhere));

		Page<Usuario> pageUsuario = usuarioRepository.findAll(queryWhere, PageRequest.of(
				paginacion.getPageNumber(), paginacion.getRegistrosPorPagina(), 
				paginacion.getSortDirection(), getSortColumn(paginacion.getiSortCol_0())));	

		Map<String, Object> datos = getDataTableData(paginacion, pageUsuario.getContent());

		return datos;

	}

	private BooleanBuilder generateWhereBuilder(String username, String estado) {
		BooleanBuilder queryWhere = new BooleanBuilder();
		QUsuario qUsuario = QUsuario.usuario;

		if(username != null && !username.equals("")) {
			queryWhere.and(qUsuario.username.containsIgnoreCase(username));
		}

		if(estado != null && !estado.equals("")) {
			queryWhere.and(qUsuario.activo.eq(Util.getBooleanValueEstado(estado)));
		}

		return queryWhere; 
	}

	private String getSortColumn(Integer sortColumn) {
		Map<Integer, String> columns = new HashMap<Integer, String>();
		columns.put(1, "username");
		columns.put(2, "activo");

		return columns.get(sortColumn);
	}

	private Map<String, Object> getDataTableData(PaginacionDTO paginacion, List<Usuario> listUsuarios){

		Map<String, Object> datos = new HashMap<String, Object>();

		datos.put("sEcho", paginacion.getsEcho());
		datos.put("iTotalRecords", paginacion.getCountResult());
		datos.put("iTotalDisplayRecords", paginacion.getCountResult());

		List<String[]> listas = new ArrayList<String[]>();

		for (Usuario usuario: listUsuarios) {

			String checkbox ="<input type=\"checkbox\" name=\"checkBoxRow\" class=\"flat\" value=\"" + usuario.getId() + "\"/>";

			String[] aaDato = {
						checkbox,
						usuario.getUsername(),
						Util.obtenerNombreEstado(usuario.isActivo())
					};
			listas.add(aaDato);
		}

		datos.put("aaData", listas);

		return datos;
	}

	public Map<String, Object> save(Usuario usuario){
		Map<String, Object> retorno = null;
		Map<String, Object> notificacion = null;
		
		retorno = validaciones(usuario);
		if(retorno != null) {
			return retorno;
		}

		usuario.setPassword(encodePassword(usuario.getPassword()));

		try {
			usuario = usuarioRepository.save(usuario);
			notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_REGISTRO_CORRECTO);
		} catch (DataAccessException dataAccessException) {
			String titleError = "Error al intentar registrar el Usuario " + usuario.getUsername();
			notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
		}

		retorno = new HashMap<String, Object>();
		retorno.put("notificacion", notificacion);
		retorno.put("id", usuario.getId());
		retorno.put("estado", true);

		return retorno;
	}
	
	private boolean existsUserByUsername(String username, Integer id) {
		if(id == null) {
			id = 0;
		}
		if(usuarioRepository.countByUsernameAndId(username, id) > 0) {
			return true;
		}
		return false;
	}
	
	private String encodePassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public boolean comparePassword(String passwordIngresado, String encodePassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(passwordIngresado, encodePassword);
	}
	
	public Map<String, Object> update(Usuario usuario){
		return this.update(usuario, false);
	}
	
	private Map<String, Object> validaciones(Usuario usuario){
		Map<String, Object> retorno = null;
		Map<String, Object> notificacion = null;

		if(usuario.getUsername().contains(" ")) {
			retorno = new HashMap<String, Object>();
			notificacion = Util.crearNotificacionError("Error", 
					"El nombre de usuario no puede contener espacios en blanco");
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}

		if(existsUserByUsername(usuario.getUsername(), usuario.getId())){
			retorno = new HashMap<String, Object>();
			notificacion = Util.crearNotificacionError("Error", "El nombre de usuario ya esta registrado en el sistema.");
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}
		return retorno;
	}

	public Map<String, Object> update(Usuario usuario, boolean updatePassword){
		Map<String, Object> retorno = null;
		Map<String, Object> notificacion = null;
		
		retorno = validaciones(usuario);
		if(retorno != null) {
			return retorno;
		}
		if(usuario.getId() != null){

			Usuario actual = this.get(usuario.getId());
			actual.setUsername(usuario.getUsername());
			actual.setActivo(usuario.isActivo());
			actual.setExpirarUsuario(usuario.isExpirarUsuario());
			actual.setFechaExpiracionUsuario(usuario.getFechaExpiracionUsuario());
			
			if(updatePassword) {
				actual.setPassword(encodePassword(usuario.getPassword()));
			}

			try {
				this.saveUsuario(actual);
				notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_ACTUALIZACION_CORRECTA);
			} catch (DataAccessException dataAccessException) {
				String titleError = "Error al intentar actualizar el Usuario " + usuario.getUsername();
				notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
			}

		}else{
			notificacion = Util.notificacionErrorDelSistema();
		}
		
		retorno = new HashMap<String, Object>();
		retorno.put("notificacion", notificacion);
		retorno.put("id", usuario.getId());
		retorno.put("estado", true);

		return retorno;
	}

	public Map<String, Object> delete(Integer[] ids){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notifiaccion = null;

		boolean estadoEliminacion = true;
		String textoNotificacion = Constantes.MENSAJE_REGISTRO_ELIMINADO;
		if(ids.length > 1){
			textoNotificacion = Constantes.MENSAJE_REGISTROS_ELIMINADOS;
		}
		notifiaccion = Util.crearNotificacionInfo("Informacion", textoNotificacion);
		for(Integer id : ids){
			Usuario usuario = this.get(id);

			try {
				this.delete(usuario);
			} catch (DataAccessException dataAccessException) {
				String titleError = "Error al intentar eliminar el Usuario " + usuario.getUsername();
				notifiaccion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
				estadoEliminacion = false;
			}
		}

		retorno.put("notificacion", notifiaccion);
		retorno.put("estado", estadoEliminacion);
		return retorno;
	}
	
	public void updateRolPorDefecto(Usuario usuario, Rol rol) {
		usuario.setRolPorDefecto(rol);
		this.update(usuario);
	}
	
	@Transactional
	private Usuario saveUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	private void delete(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

}
