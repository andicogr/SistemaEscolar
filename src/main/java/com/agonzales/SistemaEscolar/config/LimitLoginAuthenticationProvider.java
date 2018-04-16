package com.agonzales.SistemaEscolar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.agonzales.SistemaEscolar.domain.UserAttempt;
import com.agonzales.SistemaEscolar.service.UserAttemptService;
import com.agonzales.SistemaEscolar.service.UsuarioService;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider{

	@Autowired
	private UserAttemptService userAttempService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) 
          throws AuthenticationException {

		try {
	
			Authentication auth = super.authenticate(authentication);

			userAttempService.resetUserAttempt(authentication.getName());
	
			return auth;

		} catch (CredentialsExpiredException e) {	
			throw new CredentialsExpiredException("La cuenta de usuario no tiene roles asignados");
	  
		} catch (AccountExpiredException e) {	
			throw new AccountExpiredException("La cuenta de usuario ha expirado");
	  
		} catch (DisabledException e) {	
			throw new DisabledException("El usuario esta inactivo");
	  
		} catch (BadCredentialsException | UsernameNotFoundException e) {	

			UserAttempt userAttempt = userAttempService.registrarUserAttemp(authentication.getName());
			
			if(userAttempt.isGotMaxAttempts()) {
				usuarioService.blockUserByUsername(authentication.getName());
			}

			throw new BadCredentialsException("Usuario y/o Clave incorrectos");
		
		} catch (LockedException e){

			String lockedMessage = "La cuenta de usuario está bloqueada";
			UserAttempt userAttemp = userAttempService.findByUsername(authentication.getName());
			if(userAttemp != null) {
				lockedMessage = "La cuenta de usuario est&aacute; bloqueada por "
						+ "intentar ingresar mas de " + userAttemp.getAttempts() + " veces sin exito";
			}
		
			throw new LockedException(lockedMessage);
		}

	}

}
