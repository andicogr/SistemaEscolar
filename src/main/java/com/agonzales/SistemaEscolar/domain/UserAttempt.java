package com.agonzales.SistemaEscolar.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserAttempt implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int MAX_USER_ATTEMPTS = 3; 

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	private String username;
	
	private Integer attempts;

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

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	
	public boolean isGotMaxAttempts() {
		if(attempts > MAX_USER_ATTEMPTS) {
			return true;
		}
		return false;
	}
	
	public UserAttempt() {
		
	}

	public UserAttempt(String username) {
		this.username = username;
		this.attempts = 1;
	}
}
