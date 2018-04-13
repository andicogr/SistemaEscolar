package com.agonzales.SistemaEscolar.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.EntidadAuditoria;

@Entity
@Table(name="usuario")
public class Usuario extends EntidadAuditoria{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(length=20, nullable=false)
	private String username;

	@Column(length=20, nullable=false)
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="usuario", cascade=CascadeType.ALL)
	private List<UsuarioRol> roles;
	
	private boolean activo;

	private boolean bloqueado;
	
	@Column(name="expirar_usuario")
	private boolean expirarUsuario;
	
	@Column(name="fecha_expirancion_usuario")
	private Date fechaExpiracionUsuario;
	
	@ManyToOne
	@JoinColumn(name="rol_id_por_defecto")
	private Rol rolPorDefecto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UsuarioRol> getRoles() {
		return roles;
	}

	public void setRoles(List<UsuarioRol> roles) {
		this.roles = roles;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public boolean isExpirarUsuario() {
		return expirarUsuario;
	}

	public void setExpirarUsuario(boolean expirarUsuario) {
		this.expirarUsuario = expirarUsuario;
	}

	public Date getFechaExpiracionUsuario() {
		return fechaExpiracionUsuario;
	}

	public void setFechaExpiracionUsuario(Date fechaExpiracionUsuario) {
		this.fechaExpiracionUsuario = fechaExpiracionUsuario;
	}

	public Rol getRolPorDefecto(){
		Rol rol = null;
		if(this.rolPorDefecto == null){
			List<Rol> roles = getRolesActivos();
			if(!roles.isEmpty()){
				rol = roles.get(0);
			}
		}else {
			rol = this.rolPorDefecto;
		}
		return rol;
	}

	public void setRolPorDefecto(Rol rolPorDefecto) {
		this.rolPorDefecto = rolPorDefecto;
	}

	public boolean isRolesActivos(){
		for(UsuarioRol rol : getRoles()){
			if(rol.isUsuarioRolYRolActivo()){
				return true;
			}
		}
		return false;
	}

	public boolean isUsuarioExpirado(){
		if(isExpirarUsuario() && getFechaExpiracionUsuario().compareTo(new Date()) > 0){
			return true;
		}
		return false;
	}

	public List<Rol> getRolesActivos(){
		List<Rol> listaRolesActivos = new ArrayList<Rol>();
		for(UsuarioRol usuarioRol : getRoles()){
			if(usuarioRol.isUsuarioRolYRolActivo()){
				listaRolesActivos.add(usuarioRol.getRol());
			}
		}
		return listaRolesActivos;
	}
	
	public Date getFechaExpiracionUsuarioValidandoNull(){
		if(getFechaExpiracionUsuario() != null){
			return getFechaExpiracionUsuario();
		}
		return new Date();
	}
	
	public String getFechaExpiracionUsuarioConFormato(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_FECHA_DDMMYYYY);
		String fechaFormateada = simpleDateFormat.format(getFechaExpiracionUsuarioValidandoNull());
		return fechaFormateada;
	}

	public Usuario() {
		
	}
	
	public Usuario(String username, boolean activo) {
		this.username = username;
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Usuario [" + (id != null ? "id=" + id + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (password != null ? "password=" + password + ", " : "") + "activo=" + activo + ", bloqueado="
				+ bloqueado + ", expirarUsuario=" + expirarUsuario + ", "
				+ (fechaExpiracionUsuario != null ? "fechaExpiracionUsuario=" + fechaExpiracionUsuario + ", " : "")
				+ (rolPorDefecto != null ? "rolPorDefecto=" + rolPorDefecto : "") + "]";
	}

}
