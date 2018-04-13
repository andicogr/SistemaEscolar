package com.agonzales.SistemaEscolar.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.agonzales.SistemaEscolar.util.EntidadAuditoria;
import com.agonzales.SistemaEscolar.util.Util;

@Entity
@Table(name="data_integrity",indexes = {@Index(name = "data_integrity_index", columnList = "entidad, entidadId")})
public class DataIntegrity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String entidad;
	
	private Integer entidadId;
	
	private String md5Code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public Integer getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Integer entidadId) {
		this.entidadId = entidadId;
	}

	public String getMd5Code() {
		return md5Code;
	}

	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}

	public DataIntegrity() {
		
	}
	
	public DataIntegrity(EntidadAuditoria object) {
		this.entidad = object.getClass().getSimpleName();
		this.entidadId = object.getId();
		this.md5Code = Util.toMD5(object.toString());
	}

}
