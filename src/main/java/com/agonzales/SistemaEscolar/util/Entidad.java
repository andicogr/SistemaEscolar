package com.agonzales.SistemaEscolar.util;

import java.util.Date;

public abstract interface Entidad {
		
	public static final String ID = "id";
	
	public abstract Integer getId();
	 
	public abstract void setId(Integer paramInteger);
	  
	public abstract String getLabel();
	
	public abstract void setUsuarioCreacion(Integer usuarioCreacion);
	
	public abstract Integer getUsuarioCreacion();
	
	public abstract void setUsuarioActualizacion(Integer usuarioActualizacion);
	
	public abstract Integer getUsuarioActualizacion();
	
	public abstract Date getFechaCreacion();
	
	public abstract void setFechaCreacion(Date fechaCreacion);
	
	public abstract Date getFechaActualizacion();
	
	public abstract void setFechaActualizacion(Date fechaActualizacion);

}
