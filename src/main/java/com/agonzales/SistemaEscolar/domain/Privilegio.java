package com.agonzales.SistemaEscolar.domain;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agonzales.SistemaEscolar.util.EntidadAuditoria;
import com.agonzales.SistemaEscolar.util.Util;

@Entity
@Table(name="privilegio")
public class Privilegio extends EntidadAuditoria{
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=50, nullable=true)
	private String nombre;
	
	@Column(length=250)
	private String descripcion;
	
	private boolean activo;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="padre_id")
	private Privilegio privilegioPadre;

	@OneToMany(mappedBy="privilegioPadre", fetch = FetchType.EAGER)
	private List<Privilegio> privilegios;

	private boolean padre;
	
	private Integer orden;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Privilegio getPrivilegioPadre() {
		return privilegioPadre;
	}

	public void setPrivilegioPadre(Privilegio privilegioPadre) {
		this.privilegioPadre = privilegioPadre;
	}

	public List<Privilegio> getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(List<Privilegio> privilegios) {
		this.privilegios = privilegios;
	}

	public boolean isPadre() {
		return padre;
	}

	public void setPadre(boolean padre) {
		this.padre = padre;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
	public String obtenerEstado(){
		return Util.obtenerNombreEstado(isActivo());
	}
	
	public String getNombrePrivilegio(){
		if(getPrivilegioPadre() != null){
			return getPrivilegioPadre().getNombre() + "_" + getNombre();
		}else{
			return getNombre();
		}
	}

	@Override
	public String toString() {
		return "Privilegio [" + (id != null ? "id=" + id + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion + ", " : "") + "activo=" + activo + ", "
				+ (privilegioPadre != null ? "privilegioPadre=" + privilegioPadre + ", " : "")
				+ (privilegios != null ? "privilegios=" + privilegios + ", " : "") + "padre=" + padre + ", "
				+ (orden != null ? "orden=" + orden : "") + "]";
	}

}
