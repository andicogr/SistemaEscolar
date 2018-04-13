package com.agonzales.SistemaEscolar.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarPassword {
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		for (int i = 0; i < 10; i++) {
			String password = "admin";
			String nuevoPassword = encoder.encode(password);
			System.out.println(password + " - " + nuevoPassword + " - Match: " 
					+ encoder.matches(password, nuevoPassword));
			i++;
			
		}
		
	}

}
