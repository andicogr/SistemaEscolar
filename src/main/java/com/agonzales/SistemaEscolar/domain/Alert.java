package com.agonzales.SistemaEscolar.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Alert implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="from_usuario_id", nullable=true)
	private Usuario fromUsuario;

	@ManyToOne
	@JoinColumn(name="to_usuario_id", nullable=false)
	private Usuario toUsuario;

	@Column(nullable=false)
	private Date date;

	@Column(nullable=false)
	private String message;

	private boolean read;

	private Date readDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getFromUsuario() {
		return fromUsuario;
	}

	public void setFromUsuario(Usuario fromUsuario) {
		this.fromUsuario = fromUsuario;
	}

	public Usuario getToUsuario() {
		return toUsuario;
	}

	public void setToUsuario(Usuario toUsuario) {
		this.toUsuario = toUsuario;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	@Override
	public String toString() {
		return "Alert [" + (id != null ? "id=" + id + ", " : "")
				+ (toUsuario != null ? "toUsuario=" + toUsuario.getUsername() + ", " : "")
				+ (date != null ? "date=" + date + ", " : "") + (message != null ? "message=" + message + ", " : "")
				+ "read=" + read + ", " + (readDate != null ? "readDate=" + readDate : "") + "]";
	}

}
