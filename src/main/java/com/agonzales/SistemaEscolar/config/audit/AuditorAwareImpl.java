package com.agonzales.SistemaEscolar.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication autentication = SecurityContextHolder.getContext().getAuthentication();
		if(autentication == null) {
			return Optional.empty();
		}
		User user = (User) autentication.getPrincipal();
		return Optional.of(user.getUsername());
	}

}
