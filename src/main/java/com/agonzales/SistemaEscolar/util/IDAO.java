package com.agonzales.SistemaEscolar.util;

import java.util.List;
import java.util.Map;


public abstract interface IDAO<T extends Entidad> {

	public abstract T get(Integer paramInteger);
	  
	public abstract List<T> getTodos();
	  
	public abstract T guardar(T paramT, Integer uid);
	  
	public abstract boolean eliminar(T paramT);
	
	public abstract boolean isUniqueValue(String nombreCampo, String valorCampo, Integer idRegistroActual);
	
	public abstract List<T> listarJson(PaginacionDTO paginacion, Map<String, Object> columnas);
	
	public abstract Number totalListaJson();

}
