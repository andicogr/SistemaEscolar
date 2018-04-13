package com.agonzales.SistemaEscolar.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println(rawPassword.toString());
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		System.out.println(rawPassword.toString() + " - " + encodedPassword);
		
		if(rawPassword.toString().equals(encodedPassword)) {
			return true;
		}
		return false;
	}

}
