package com.agonzales.SistemaEscolar.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.agonzales.SistemaEscolar.util.EntidadAuditoria;
import com.agonzales.SistemaEscolar.util.Util;

@Entity
@Table(name="usuario_rol")
@NamedNativeQuery(name="UsuarioRol.findActiveRoleByUserId",
query="SELECT rol.id, rol.nombre " + 
		"FROM usuario_rol usuarioRol " + 
		"INNER JOIN Rol rol ON rol.id = usuarioRol.rol_id " + 
		"WHERE usuarioRol.usuario_id = :userId",
resultSetMapping="UsuarioRol.findActiveRoleByUserId")
@SqlResultSetMapping(name="UsuarioRol.findActiveRoleByUserId",
classes = {
@ConstructorResult(
	targetClass=Rol.class,
		columns = {
			@ColumnResult(name = "id", type = Integer.class),
			@ColumnResult(name = "nombre", type = String.class),
		})
})
public class UsuarioRol extends EntidadAuditoria{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="usuario_id", nullable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="rol_id", nullable=false)
	private Rol rol;
	
	private boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isRolActivo(){
		if(getRol().isActivo()){
			return true;
		}
		return false;
	}
	
	public List<Privilegio> getPrivilegios(){
		return getRol().getPrivilegios();
	}

	public boolean isUsuarioRolYRolActivo(){
		if(isActivo() && isRolActivo()){
			return true;
		}
		return false;
	}
	
	public String getNombreRol(){
		if(getRol() != null){
			return getRol().getNombre();
		}
		return "";
	}
	
	public String getEstadoUsuarioRol(){
		return Util.obtenerNombreEstado(isActivo());
	}
	
	public Integer getUsuarioId(){
		return getUsuario().getId();
	}
	
	public Integer getRolId(){
		return getRol().getId();
	}

	@Override
	public String toString() {
		return "UsuarioRol [" + (id != null ? "id=" + id + ", " : "")
				+ (usuario != null ? "usuario=" + usuario.getId() + ", " : "") + (rol != null ? "rol=" + rol.getId() + ", " : "")
				+ "activo=" + activo + "]";
	}

}
