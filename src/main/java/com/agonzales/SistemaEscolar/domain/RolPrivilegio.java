package com.agonzales.SistemaEscolar.domain;

import java.io.Serializable;

public class RolPrivilegio implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer idRol;

	private Integer idPrivilegio;

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public Integer getIdPrivilegio() {
		return idPrivilegio;
	}

	public void setIdPrivilegio(Integer idPrivilegio) {
		this.idPrivilegio = idPrivilegio;
	}

}
