package com.agonzales.SistemaEscolar.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class DAO<T extends Entidad> implements IDAO<T>{

	private Class<Entidad> clazz;
	@PersistenceContext
	protected EntityManager em;
	
	private String getNameClass(){
		Entity e = (Entity)this.clazz.getAnnotation(Entity.class);
		
		String nombre = null;
		if ((e == null) || (e.name() == null) || (e.name().length() == 0)) {
			nombre = this.clazz.getSimpleName();
		} else {
			nombre = e.name();
		}
		return nombre;
	}
	  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DAO(){
		this.clazz = ((Class)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
  
	@SuppressWarnings("unchecked")
	public T get(Integer id){
		try{
			//return (Entidad) this.em.find(this.clazz, id);
			return (T) this.em.find(this.clazz, id);
		}
		catch (Exception e) {}
		return null;
	}
  
	@SuppressWarnings("unchecked")
	public List<T> getTodos(){
		String nombre = this.getNameClass();

		String sql = "SELECT e FROM " + nombre + " e";
		return this.em.createQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T guardar(T objeto, Integer uid){
		if (objeto.getId() != null){
			objeto.setUsuarioActualizacion(uid);
			objeto.setFechaActualizacion(new Date());
			this.em.merge(objeto);
			this.em.flush();
			return (T) this.em.find(this.clazz, objeto.getId());
		}
		objeto.setUsuarioCreacion(uid);
		objeto.setFechaCreacion(new Date());
		this.em.persist(objeto);
		this.em.flush();
		this.em.refresh(objeto);

		return objeto;

	}
  
	public boolean eliminar(T objeto){
		try {
			this.em.remove(objeto);
			return true;
		} catch (Exception e) {
			// TODO: Imprimir excepcion
			return false;
		}
		
	}
	
	public boolean isUniqueValue(String nombreCampo, String valorCampo, Integer idRegistroActual){
		boolean retorno = false;
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT COUNT(a) FROM ");
		query.append(this.getNameClass() + " a WHERE ");
		query.append("upper(a." + nombreCampo + ") = :valorCampo ");
		if(idRegistroActual != null) query.append("AND a.id <> :idRegistroActual ");

		Query q =  this.em.createQuery(query.toString());
		
		q.setParameter("valorCampo", valorCampo.toUpperCase());
		if(idRegistroActual != null) q.setParameter("idRegistroActual", idRegistroActual);
		
		long count  = (Long) q.getSingleResult();
		
		if(count > 0){
			retorno = true;
		}

		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listarJson(PaginacionDTO paginacion, Map<String, Object> columnas){
		String sql = "FROM " + this.getNameClass() + " a ";
		
		boolean multiCompania = (Boolean) VariablesSession.getAttribute(Constantes.MULTICOMPANIA_ACTIVADO);

		if(!multiCompania){
			sql += " WHERE a.compania.id = 2";
		}

		sql += Util.OrderByPagination(columnas, paginacion.getiSortCol_0(), paginacion.getsSortDir_0());
		
		Query q = em.createQuery(sql);

		if(paginacion.getiDisplayLength()!=-1){
			q.setMaxResults(paginacion.getiDisplayLength());	
		}
		q.setFirstResult(paginacion.getiDisplayStart());	
	
		try{
			return q.getResultList();
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public Number totalListaJson() {

		String sql="select count(a) from " + this.getNameClass() + " a ";
		
		boolean multiCompania = (Boolean) VariablesSession.getAttribute(Constantes.MULTICOMPANIA_ACTIVADO);
		
		if(!multiCompania){
			sql += " WHERE a.compania.id = 2";
		}

		/*List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		Map<String, Object> datos = new HashMap<String, Object>();
		if(!busqueda.getCodigo().equals("")){datos.put("1", "upper(a.codigo) like upper(:codigo)");}
		if(!busqueda.getNombres().equals("")){datos.put("2", "(upper(a.persona.nombres) like upper(:nombres) or upper(a.persona.apellidos) like upper(:nombres))");}
		lista.add(datos);
		
		sql += Util.query(lista);*/
		
		Query q = em.createQuery(sql);
		
		/*if(!busqueda.getCodigo().equals("")){q.setParameter("codigo","%"+busqueda.getCodigo()+"%");}
		if(!busqueda.getNombres().equals("")){q.setParameter("nombres","%"+busqueda.getNombres()+"%");}*/
			
		try{
			return (Number) q.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}

}
