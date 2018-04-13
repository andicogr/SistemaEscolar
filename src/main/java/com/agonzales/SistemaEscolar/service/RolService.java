package com.agonzales.SistemaEscolar.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agonzales.SistemaEscolar.domain.Privilegio;
import com.agonzales.SistemaEscolar.domain.QRol;
import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.repository.RolPrivilegioRespository;
import com.agonzales.SistemaEscolar.repository.RolRepository;
import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.PaginacionDTO;
import com.agonzales.SistemaEscolar.util.SqlExceptionMessage;
import com.agonzales.SistemaEscolar.util.Util;
import com.querydsl.core.BooleanBuilder;

@Service
public class RolService {

	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private RolPrivilegioRespository rolPrivilegioRepository;
	
	public Rol get(Integer id) {
		Optional<Rol> rol = rolRepository.findById(id);
		return rol.get();
	}

	public Map<String, Object> getDataTableJson(PaginacionDTO paginacion, 
			String nombre, String estado){

		if(paginacion.getiDisplayLength()==null){
			return null;
		}

		BooleanBuilder queryWhere = generateWhereBuilder(nombre, estado);

		paginacion.setCountResult((int) rolRepository.count(queryWhere));

		Page<Rol> pageRol = rolRepository.findAll(queryWhere, PageRequest.of(
				paginacion.getPageNumber(), paginacion.getRegistrosPorPagina(), 
				paginacion.getSortDirection(), getSortColumn(paginacion.getiSortCol_0())));	

		Map<String, Object> datos = getDataTableData(paginacion, pageRol.getContent());

		return datos;

	}

	public Map<String, Object> save(Rol rol){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		if(existsRolByNombre(rol.getNombre(), rol.getId())){
			notificacion = Util.crearNotificacionError("Error", 
					"El nombre del rol ya esta registrado en el sistema.");
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}

		try {
			rol = this.saveRol(rol);
			rolPrivilegioRepository.setDefaultPrivilegioUsuario(rol.getId());
			notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_REGISTRO_CORRECTO);
		} catch (DataAccessException dataAccessException) {
			String titleError = "Error al intentar registrar el Rol " + rol.getNombre();
			notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
		}

		retorno.put("notificacion", notificacion);
		retorno.put("id", rol.getId());
		retorno.put("estado", true);

		return retorno;
	}

	public Map<String, Object> update(Rol modifiedRole){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		if(existsRolByNombre(modifiedRole.getNombre(), modifiedRole.getId())){
			notificacion = Util.crearNotificacionError("Error", "El nombre del rol ya esta registrado en el sistema.");
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}

		if(modifiedRole.getId() != null){
			
			Rol currentRole = this.get(modifiedRole.getId());
			currentRole.setNombre(modifiedRole.getNombre());
			currentRole.setDescripcion(modifiedRole.getDescripcion());
			currentRole.setActivo(modifiedRole.isActivo());
			try {
				currentRole = this.saveRol(currentRole);
				notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_ACTUALIZACION_CORRECTA);
			} catch (DataAccessException dataAccessException) {
				String titleError = "Error al intentar actualizar el Rol " + modifiedRole.getNombre();
				notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
			}

		}else{
			notificacion = Util.notificacionErrorDelSistema();
		}

		retorno.put("notificacion", notificacion);
		retorno.put("id", modifiedRole.getId());
		retorno.put("estado", true);

		return retorno;
	}

	public Map<String, Object> delete(Integer[] ids){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		if(isRolPrincipal(ids)){
			notificacion = Util.crearNotificacion("error", "Error", 
					"No se puede eliminar un rol principal del sistema.", 5000);
			retorno.put("notificacion", notificacion);
			retorno.put("estado", false);
			return retorno;
		}

		boolean estadoEliminacion = true;
		String textoNotificacion = Constantes.MENSAJE_REGISTRO_ELIMINADO;
		if(ids.length > 1){
			textoNotificacion = Constantes.MENSAJE_REGISTROS_ELIMINADOS;
		}
		notificacion = Util.crearNotificacionInfo("Informacion", textoNotificacion);

		for(Integer id : ids){
			Rol rol = this.get(id);

			try {
				this.delete(rol);
			} catch (DataAccessException dataAccessException) {
				String titleError = "Error al intentar eliminar el Rol " + rol.getNombre();
				notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
				estadoEliminacion = false;
			}
		}

		retorno.put("notificacion", notificacion);
		retorno.put("estado", estadoEliminacion);
		return retorno;
	}
	
	public boolean isRolPrincipal(Integer[] ids){

		for(Integer id : ids){
			if(Arrays.asList(Constantes.ROLES_PRINCIPALES).contains(id)){
				return true;
			}
		}
		return false;
	}

	private BooleanBuilder generateWhereBuilder(String nombre, String estado) {
		BooleanBuilder queryWhere = new BooleanBuilder();
		QRol qRol = QRol.rol;

		if(nombre != null && !nombre.equals("")) {
			queryWhere.and(qRol.nombre.containsIgnoreCase(nombre));
		}

		if(estado != null && !estado.equals("")) {
			queryWhere.and(qRol.activo.eq(Util.getBooleanValueEstado(estado)));
		}

		return queryWhere; 
	}

	private String getSortColumn(Integer sortColumn) {
		Map<Integer, String> columns = new HashMap<Integer, String>();
		columns.put(1, "nombre");
		columns.put(2, "activo");

		return columns.get(sortColumn);
	}

	private Map<String, Object> getDataTableData(PaginacionDTO paginacion, List<Rol> listRol){

		Map<String, Object> datos = new HashMap<String, Object>();

		datos.put("sEcho", paginacion.getsEcho());
		datos.put("iTotalRecords", paginacion.getCountResult());
		datos.put("iTotalDisplayRecords", paginacion.getCountResult());

		List<String[]> listas = new ArrayList<String[]>();

		for (Rol rol: listRol) {

			String checkbox ="<input type=\"checkbox\" name=\"checkBoxRow\" class=\"flat\" value=\"" + rol.getId() + "\"/>";

			String[] aaDato = {
						checkbox,
						rol.getNombre(),
						Util.obtenerNombreEstado(rol.isActivo())
					};
			listas.add(aaDato);
		}

		datos.put("aaData", listas);

		return datos;
	}
	
	private boolean existsRolByNombre(String nombre, Integer id) {
		if(id == null) {
			id = 0;
		}
		if(rolRepository.countByNombreAndId(nombre, id) > 0) {
			return true;
		}
		return false;
	}

	public Map<String, Object> actualizarPrivilegios(Integer idRol, Integer[] idPrvivilegios){
		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> notificacion = null;

		Rol rol = this.get(idRol);
		List<Privilegio> listaPrivilegios = new ArrayList<Privilegio>();
		for(Integer idPrivilegio: idPrvivilegios){
			Privilegio privilegio = new Privilegio();
			privilegio.setId(idPrivilegio);
			listaPrivilegios.add(privilegio);
		}
		
		if(idRol != Constantes.ROL_ADMINISTRADOR_ID) {
			Privilegio privilegio = new Privilegio();
			privilegio.setId(Constantes.PRIVILEGIO_USUARIO);
			listaPrivilegios.add(privilegio);
		}

		rol.setPrivilegios(listaPrivilegios);

		try {
			this.saveRol(rol);
			notificacion = Util.crearNotificacionSuccess("Correcto", Constantes.MENSAJE_PRIVILEGIOS_ACTUALIZADOS_CORRECTAMENTE);
		} catch (DataAccessException dataAccessException) {
			String titleError = "Error al intentar actualizar privilegios del Rol " + rol.getNombre();
			notificacion = SqlExceptionMessage.getInstance().getMessage(dataAccessException, titleError);
		}

		retorno.put("notificacion", notificacion);
		retorno.put("id", rol.getId());
		retorno.put("estado", true);

		return retorno;
	}
	
	public List<Rol> listUnassignedRoleByUserId(Integer userId){
		List<Rol> listaUnassignedRole = rolRepository.findUnassignedRoleByUserId(userId);
		return listaUnassignedRole;
	}
	
	@Transactional
	private void delete(Rol rol) {
		rolRepository.delete(rol);
	}
	
	@Transactional
	private Rol saveRol(Rol rol) {
		return rolRepository.save(rol);
	}

}
