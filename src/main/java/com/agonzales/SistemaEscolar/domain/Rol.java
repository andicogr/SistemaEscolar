package com.agonzales.SistemaEscolar.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.agonzales.SistemaEscolar.util.EntidadAuditoria;
import com.agonzales.SistemaEscolar.util.Util;


@Entity
@Table(name="rol")
@NamedNativeQuery(name="Rol.findUnassignedRoleByUserId",
		query="SELECT rol.id, rol.nombre " + 
				"FROM (SELECT id, rol_id FROM usuario_rol WHERE usuario_id = :userId) usuarioRol " + 
				"RIGHT JOIN Rol rol ON rol.id = usuarioRol.rol_id " + 
				"WHERE usuarioRol.id IS null",
		resultSetMapping="Rol.findUnassignedRoleByUserId")
@SqlResultSetMapping(name="Rol.findUnassignedRoleByUserId",
	classes = {
		@ConstructorResult(
			targetClass=Rol.class,
				columns = {
					@ColumnResult(name = "id", type = Integer.class),
					@ColumnResult(name = "nombre", type = String.class),
				})
	})
public class Rol extends EntidadAuditoria{

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

	@ManyToMany(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="rol_privilegio", 
		joinColumns = {@JoinColumn(name = "rol_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "privilegio_id")})
	private List<Privilegio> privilegios;

	private boolean activo;

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

	public List<Privilegio> getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(List<Privilegio> privilegios) {
		this.privilegios = privilegios;
	}


	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String obtenerEstado(){
		return Util.obtenerNombreEstado(isActivo());
	}

	public Rol(){
		
	}
	
	public Rol(Integer id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}
	
	public Rol(Integer id){
		this.id = id;
	}

	@Override
	public String toString() {
		return "Rol [" + (id != null ? "id=" + id + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion + ", " : "") + "activo=" + activo + "]";
	}
	
}
